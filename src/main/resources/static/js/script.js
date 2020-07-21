const ROLES = ['ADMIN', 'USER'];

$(function () {
    updateUserTable();
    $('#save').on('click', event => saveUser(event));
    $('#edit').on('click', event => editUser(event));
    $('#delete').on('click', event => deleteUser(event));
})

function saveUser(e) {
    e.preventDefault();

    let newUser = {
        firstName: $('#addFirstName').val(),
        lastName: $('#addLastName').val(),
        age: $('#addAge').val(),
        email: $('#addUserMail').val(),
        password: $('#addPass').val(),
        roles: []
    };

    $('#addRoles').find('option:selected').each((i, option) => {
        newUser['roles'].push({'name': $(option).val()});
    });

    console.log("user:");
    console.log(newUser);

    $.ajax({
        url: '/api/admin/add',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(newUser),
        complete: function () {
            $('#list-tab').tab('show')
            updateUserTable();
        }
    })
}

function editUser(e) {
    e.preventDefault();

    let editUser = {
        id: $('#editId').val(),
        firstName: $('#editFirstName').val(),
        lastName: $('#editLastName').val(),
        age: $('#editAge').val(),
        email: $('#editEmail').val(),
        roles: []
    };

    $('#editRoles').find('option:selected').each((i, option) => {
        editUser['roles'].push({'name': $(option).val()});
    });

    console.log("user:");
    console.log(editUser);

    $.ajax({
        url: '/api/admin/user',
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(editUser),
        complete: function () {
            $('#edit-modal').modal('hide');
            updateUserTable();
        }
    });
}

function deleteUser(e) {
    e.preventDefault();

    let deleteUSer = {
        id: $('#deleteId').val(),
        firstName: $('#deleteFirstName').val(),
        lastName: $('#deleteLastName').val(),
        age: $('#deleteAge').val(),
        email: $('#deleteEmail').val(),
        roles: []

    };

    $('#deleteRoles').find('option:selected').each((i, option) => {
        deleteUSer['roles'].push({'name': $(option).val()});
    });

    console.log("user:");
    console.log(deleteUSer);

    $.ajax({
        url: '/api/admin/delete',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(deleteUSer),
        complete: function () {
            $('#delete-modal').modal('hide');
            updateUserTable();
        }
    })
}

function updateUserTable() {
    $('#users').empty();
    $.ajax({
        type: 'GET',
        url: '/api/admin/list',
        success: function (users) {
            users.forEach(user =>
                $('#users')
                    .append("<tr> \
                                    <td>" + user.id + "</td> \
                                    <td>" + user.firstName + "</td> \
                                    <td>" + user.lastName + "</td> \
                                    <td>" + user.age + "</td> \
                                    <td>" + user.email + "</td> \
                                    <td>" + user.roles.map(role => role.name).join(' ') + "</td> \
                                    <td><button type='button' class='btn btn-info' onclick='editUserForm(" + user.id + ")'>Edit</button><td> \
                                    <td><button type='button' class='btn btn-danger' onclick='deleteUserForm(" + user.id + ")'>Delete</button><td> \
                             </tr>"))
        }
    });
}

function deleteUserForm(id) {
    console.log("id =" + id);
    $.ajax({
        url: '/api/admin/user/' + id,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (user) {

            console.log("user:");
            console.log(user);

            $('#deleteId').val(user.id);
            $('#deleteFirstName').val(user.firstName);
            $('#deleteLastName').val(user.lastName);
            $('#deleteAge').val(user.age);
            $('#deleteEmail').val(user.email);

            const deleteRoles = $('#deleteRoles');
            const userRoles = user.roles.map(r => r.name);

            console.log("userRoles:");
            console.log(userRoles);

            deleteRoles.empty();

            ROLES.forEach(role => deleteRoles.append(
                "<option value='" + role + "'"
                + (userRoles.includes(role) ? " selected" : "") + ">"
                + role + "</option>")
            );

            $('#delete-modal').modal('show');
        }
    })
}

function editUserForm(id) {
    console.log("id =" + id);
    $.ajax({
        url: '/api/admin/user/' + id,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json',
        success: function (user) {

            console.log("user:");
            console.log(user);

            $('#editId').val(user.id);
            $('#editFirstName').val(user.firstName);
            $('#editLastName').val(user.lastName);
            $('#editAge').val(user.age);
            $('#editEmail').val(user.email);

            const editRoles = $('editRoles');
            const userRoles = user.roles.map(r => r.name);

            console.log("userRoles:");
            console.log(userRoles);

            editRoles.empty();

            ROLES.forEach(role => editRoles.append(
                "<option value='" + role + "'"
                + (userRoles.includes(role) ? " selected" : "") + ">"
                + role + "</option>")
            );

            $('#edit-modal').modal('show');
        }
    })
}