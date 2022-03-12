<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </head>
    <body class="p-3 mb-2 bg-dark text-white">
        <br>
        <div class="container-fluid gx-5">
            <div class="row">
                <div class="col p-2 bg-dark">
                    <h3 class="text-center user-select-none">Add User</h3>
                    <form method="post">
                        <div class="mb-2">
                            <input type="email" class="form-control bg-secondary bg-opacity-25 text-white" name="addEmail" placeholder="Email">
                        </div>
                        <div class="mb-2">
                            <input type="text" class="form-control bg-secondary bg-opacity-25 text-white" name="addFirstName" placeholder="First Name">
                        </div>
                        <div class="mb-2">
                            <input type="text" class="form-control bg-secondary bg-opacity-25 text-white" name="addLastName" placeholder="Last Name">
                        </div>
                        <div class="mb-2">
                            <input type="password" class="form-control bg-secondary bg-opacity-25 text-white" name="addPassword" placeholder="Password">
                        </div>
                        <select class="form-select bg-secondary bg-opacity-25 text-white" name="addRole">
                                <option class="bg-dark" selected disabled hidden>Role</option>
                            <c:forEach var="role" items="${roles}">
                                <option class="bg-dark" value="${role.id}">${role.name}</option>
                            </c:forEach>
                        </select>
                        <div class="m-3 form-check">
                            <input type="checkbox" class="form-check-input" name="addActive" id="addActive">
                            <label class="form-check-label" for="addActive">Active</label>
                        </div>
                        <div class="d-grid">
                            <button type="submit" name="action" value="add" class="btn btn-primary">Add User</button>
                        </div>
                    </form>
                </div>
                <div class="p-2 col-6 bg-secondary bg-opacity-25 user-select-none">
                    <h3 class="text-center user-select-none">Manage Users</h3>
                    <form method="post">
                        <table class="table text-white">
                            <thead>
                                <tr>
                                    <th>Email</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Role</th>
                                    <th>Active</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${users}">
                                    <tr>
                                        <td>${user.email}</td>
                                        <td>${user.firstName}</td>
                                        <td>${user.lastName}</td>
                                        <td>${user.getRole().getName()}</td>
                                        <td>${user.active ? 'Y' : 'N'}</td>
                                        <td>
                                            <button type="submit" name="edit" value="${user.email}" class="btn-sm btn-warning">Edit</button>
                                            <button type="submit" name="delete" value="${user.email}" class="btn-sm btn-danger" onclick="return confirm('Are you sure you want to permanently delete ${user.firstName}?')">Delete</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="col p-2 bg-dark">
                    <h3 class="text-center user-select-none">Edit User</h3>
                    <form method="post">
                        <div class="mb-2">
                            <input type="email" class="form-control bg-secondary bg-opacity-25 text-white pe-none user-select-none" name="editEmail" placeholder="Email" value="${userEdit.email}">
                        </div>
                        <div class="mb-2">
                            <input type="text" class="form-control bg-secondary bg-opacity-25 text-white" name="editFirstName" placeholder="First Name" value="${userEdit.firstName}">
                        </div>
                        <div class="mb-2">
                            <input type="text" class="form-control bg-secondary bg-opacity-25 text-white" name="editLastName" placeholder="Last Name" value="${userEdit.lastName}">
                        </div>
                        <div class="mb-2">
                            <input type="password" class="form-control bg-secondary bg-opacity-25 text-white" name="editPassword" placeholder="Password" value="${userEdit.password}">
                        </div>
                        <select class="form-select bg-secondary bg-opacity-25 text-white" name="editRole">
                            <option class="bg-dark" selected hidden value="${userEdit.role.id}">
                                <c:choose>
                                    <c:when test="${userEdit != null}">
                                        ${userEdit.role.name}
                                    </c:when>
                                    <c:otherwise>
                                        Role
                                    </c:otherwise>
                                </c:choose>
                            </option>
                            <c:forEach var="role" items="${roles}">
                                <option class="bg-dark" value="${role.id}">${role.name}</option>
                            </c:forEach>
                        </select>
                        <div class="m-3 form-check">
                            <input type="checkbox" class="form-check-input" name="editActive" id="editActive" ${checked}>
                            <label class="form-check-label" for="editActive">Active</label>
                        </div>
                        <div class="d-grid gap-2">
                            <button type="submit" name="save" class="btn btn-primary">Save</button>
                            <button type="submit" name="cancel" class="btn btn-secondary">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <p>${message}</p>
    </body>
</html>
