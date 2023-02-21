var pageNumber = 1;
var size = 5;
var sortField = "id";
var isDESC = true;
var searchValue = "";
var search = ""

$(document).ready(function () {
    // if (!isLogin()) {
    //     window.location.replace("http://127.0.0.1:8081/login.html")
    // }
    document.getElementById("usernameStorage").innerHTML = localStorage.getItem("USERNAME");

    if (!isLogin()) {
        document.getElementById("loginIn").style.display = "none";
        document.getElementById("addNewDepartment").style.display = "none";
        document.getElementById("tabAccount").style.display = "none";
    }
    if (isLogin()) {
        document.getElementById("tabLogin").style.display = "none";
        if (localStorage.getItem("ROLE") == "EMPLOYEE") {
            document.getElementById("addNewDepartment").style.display = "none";
        }
    }

    // if (isLogin()) {
    //     if (localStorage.getItem("ROLE") == "EMPLOYEE") {
    //         document.getElementById("addNewDepartment").style.display = "none";
    //         document.getElementById("buttonUpdate").style.display = "none";
    //         document.getElementById("buttonDelete").style.display = "none";
    //     }
    // }
});

function loadNewDataDepartment() {
    $('#getAllDepartments').empty();
    getAllDepartments();
}

function isLogin() {
    if (localStorage.getItem("USERNAME")) {
        return true;
    }
    return false;
}

function logout() {
    localStorage.removeItem("ID");
    localStorage.removeItem("USERNAME");
    localStorage.removeItem("PASSWORD");
    localStorage.removeItem("ROLE");
    window.location.replace("http://127.0.0.1:8081/home.html")

}

function getAllDepartments() {
    if (searchValue) {
        search = "&search=" + searchValue
    }
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/qlNhanVien/departments" + "?pageNumber=" + pageNumber + "&size=" + size + "&sort=" + sortField + "," + (isDESC ? "desc" : "asc") + search,
        success: function (data) {
            console.log(data.content)
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            getDataListDepartment(data.content);
            pagingTableDepartments(data.totalPages);

            // if (isLogin()) {
            //     if (localStorage.getItem("ROLE") == "EMPLOYEE") {
            //         document.getElementById("addNewDepartment").style.display = "none";
            //         document.getElementById("buttonUpdate").style.display = "none";
            //         document.getElementById("buttonDelete").style.display = "none";
            //     }
            // }
        }
    })
}

function getDataListDepartment(array) {
    array.forEach(element => {

        if (isLogin()) {
            if (localStorage.getItem("ROLE") == "EMPLOYEE") {
                $('#getAllDepartments').append(
                    '<tr>' +
                    '<td>' + element.id + '</td>' +
                    '<td> <a href="" class="text-reset text-decoration-none" data-toggle="modal" data-target="#modalDepartmentDetail" onclick="getDepartmentDetail(' + element.id + ')">' + element.name + '</a> </td>' +
                    '<td>' + element.type + '</td>' +
                    '<td>' + element.totalMember + '</td>' +
                    '<td>' + element.createdDate + '</td>' +
                    '<td>' + '</td>' +
                    '<td>' + '</td>' +
                    '</tr>'
                )
            };
            if (localStorage.getItem("ROLE") == "ADMIN" || localStorage.getItem("ROLE") == "MANAGER") {
                $('#getAllDepartments').append(
                    '<tr>' +
                    '<td>' + element.id + '</td>' +
                    '<td> <a href="" class="text-reset text-decoration-none" data-toggle="modal" data-target="#modalDepartmentDetail" onclick="getDepartmentDetail(' + element.id + ')">' + element.name + '</a> </td>' +
                    '<td>' + element.type + '</td>' +
                    '<td>' + element.totalMember + '</td>' +
                    '<td>' + element.createdDate + '</td>' +
                    '<td>' + '<button class="btn btn-success" data-toggle="modal" data-target="#myModal" id="buttonUpdate" onclick="getValueDepartment(' + element.id + ')">Update</button>' + '</td>' +
                    '<td>' + '<a class="btn btn-danger" id="buttonDelete" onclick="deleteDepartment(' + element.id + ')">Xóa</a>' + '</td>' +
                    '</tr>'
                )
            }
        }

    });
}

function pagingTableDepartments(totalPages) {
    var pageButton = "";
    // nút previous
    if (totalPages > 1 && pageNumber != 1) {
        pageButton += '<li class="page-item"><a class="page-link" onclick="previousPageDepartment()">&laquo;</a></li>';
    }

    for (var i = 0; i < totalPages; i++) {
        pageButton += '<li class="page-item ' + (pageNumber == i + 1 ? "active" : "") + '">' +
            '<a class="page-link" onclick="changePageDepartment(' + (i + 1) + ')">' + (i + 1) + '</a>' +
            '</li>';
    }

    //nút next trang
    if (totalPages > 1 && totalPages > pageNumber) {
        pageButton += '<li class="page-item"><a class="page-link" onclick="nextPageDepartment()">&raquo;</a></li>';
    }
    $('#paginationDepartments').empty();
    $('#paginationDepartments').append(pageButton);
}

function changePageDepartment(page) {

    if (page == pageNumber) {
        return;
    }
    pageNumber = page;
    loadNewDataDepartment();
}

function previousPageDepartment() {
    changePageDepartment(pageNumber - 1)
}

function nextPageDepartment() {
    changePageDepartment(pageNumber + 1);
}

function changeSortDepartment(field) {
    if (field == sortField) {
        isDESC = !isDESC;
    } else {
        sortField = field;
        isDESC = true;
    }
    loadNewDataDepartment();
}

function searchDepartment() {
    var searchStringDepartment = document.getElementById("searchStringDepartment").value;
    console.log(searchStringDepartment);

    if (searchStringDepartment) {
        searchValue = searchStringDepartment;
        console.log(searchValue);
        loadNewDataDepartment();
    }
}

function resetFormDepartment() {

}

function saveDepartment() {
    var id = document.getElementById("id").value;
    if (id == null || id == "") {
        createDepartment();
        document.getElementById("formDepartment").reset;
        document.getElementById("getAccountByDepartment").reset;
    } else {
        updateDepartment();
        document.getElementById("formDepartment").reset;
        document.getElementById("getAccountByDepartment").reset;
    }
}

function getAccountByDepartmentIdIsNull() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/qlNhanVien/accounts/accountByDepartmentIdIsNull",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            data.forEach(element => {
                $('#getAccountByDepartment').append(
                    '<div class="col-4">' +
                    '<input class="form-check-input add-username" type="checkbox" value="' + element.username + '">' +
                    '<label class="form-check-label" for="username">' + element.username + '</label>' +
                    '</div>')
            });
            resetFormDepartment();
        }
    })
}

function createDepartment() {
    var name = document.getElementById("name").value;
    var type = document.getElementById("type").value;
    var listUsername = document.getElementsByClassName("add-username");
    var accounts = [];

    for (let u of listUsername) {
        var user = Object();
        if (u.checked == true) {
            user.username = u.value;
            accounts.push(user);
        }
    }

    var department = {
        name: name,
        type: type,
        accounts: accounts
    }

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/qlNhanVien/departments",
        data: JSON.stringify(department), // body, truyền dữ liệu và body
        contentType: "application/json", // type of body, kiểu dữ liệu khi truyền vào body (json, xml, text)
        // dataType: 'json', // datatype return
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            console.log(data);
            // success
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            loadNewDataDepartment();
        }
    })
}

function getValueDepartment(id) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/qlNhanVien/departments/" + id,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            console.log(data)
            document.getElementById("id").value = data.id;
            document.getElementById("name").value = data.name;
            document.getElementById("type").value = data.type;
            getAccountByDepartmentIdIsNullOrDepartmentIdIsParam(id);
        }
    });
}

function getAccountByDepartmentIdIsNullOrDepartmentIdIsParam(id) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/qlNhanVien/accounts/accountByDepartmentIdIsNullOrDepartmentIdIsParam/" + id,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            data.forEach(element => {
                if (element.departmentId == id) {
                    $('#getAccountByDepartment').append(
                        '<div class="col-4">' +
                        '<input class="form-check-input add-username" type="checkbox" value = "' + element.username + '" checked> ' +
                        '<label class="form-check-label" for="username">' + element.username + '</label>' +
                        '</div>')
                }
                else {
                    $('#getAccountByDepartment').append(
                        '<div class="col-4">' +
                        '<input class="form-check-input add-username" type="checkbox" value = "' + element.username + '" > ' +
                        '<label class="form-check-label" for="username">' + element.username + '</label>' +
                        '</div>')
                }
            });
        }
    });
}

function updateDepartment() {
    var id = document.getElementById("id").value;
    var name = document.getElementById("name").value;
    var type = document.getElementById("type").value;
    var listUsername = document.getElementsByClassName("add-username");
    var accounts = [];

    for (let u of listUsername) {
        var user = Object();
        if (u.checked == true) {
            user.username = u.value;
            accounts.push(user);
        }
    }

    var department = {
        name: name,
        type: type,
        accounts: accounts
    }

    $.ajax({
        type: "PUT",
        url: "http://localhost:8080/api/qlNhanVien/departments/" + id,
        data: JSON.stringify(department), // body, truyền dữ liệu và body
        contentType: "application/json", // type of body, kiểu dữ liệu khi truyền vào body (json, xml, text)
        // dataType: 'json', // datatype return
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            console.log(data);
            // success
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            resetFormDepartment();
            loadNewDataDepartment();
        }
    });
}

function deleteDepartment(id) {
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/api/qlNhanVien/departments/" + id,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            // success
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            loadNewDataDepartment();
        }
    });
}

function getDepartmentDetail(id) {
    $('#getDepartmentDetail').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/qlNhanVien/departments/" + id,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            console.log(data);
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            getDataDepartmentDetail(data.accounts);
        }
    });
}

function getDataDepartmentDetail(array) {
    array.forEach(element => {
        $('#getDepartmentDetail').append(
            '<tr>' +
            '<td>' + element.accountId + '</td>' +
            '<td>' + element.username + '</td>' +
            '<td>' + element.email + '</td>' +
            '</tr>'
        )
    });
}
