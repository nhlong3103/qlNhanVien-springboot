//
//Account
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
    if (!isLogin()) {
        document.getElementById("loginIn").style.display = "none";
        document.getElementById("addNewDepartment").style.display = "none";
        document.getElementById("tabAccount").style.display = "none";
    }
    if (isLogin()) {
        if (localStorage.getItem("ROLE") == "EMPLOYEE") {
            document.getElementById("addNewAccount").style.display = "none";
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

function isLogin() {
    if (localStorage.getItem("USERNAME")) {
        return true;
    }
    return false;
}

function GetAllAccounts() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/qlNhanVien/accounts" + "?pageNumber=" + pageNumber + "&size=" + size + "&sort=" + sortField + "," + (isDESC ? "desc" : "asc") + search,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            getDataListAccounts(data.content);
            pagingTableAccounts(data.totalPages);
        }
    })
}


function getDataListAccounts(array) {
    array.forEach(element => {
        if (isLogin()) {
            if (localStorage.getItem("ROLE") == "EMPLOYEE") {
                $('#GetAllAccounts').append(
                    '<tr>' +
                    '<td>' + element.id + '</td>' +
                    '<td>' + element.username + '</td>' +
                    '<td>' + element.email + '</td>' +
                    '<td>' + element.departmentName + '</td>' +
                    '<td>' + '</td>' +
                    '<td>' + '</td>' +
                    '</tr>'
                )
            };
            if (localStorage.getItem("ROLE") == "ADMIN" || localStorage.getItem("ROLE") == "MANAGER") {
                $('#GetAllAccounts').append(
                    '<tr>' +
                    '<td>' + element.id + '</td>' +
                    '<td>' + element.username + '</td>' +
                    '<td>' + element.email + '</td>' +
                    '<td>' + element.departmentName + '</td>' +
                    '<td>' + '<button class="btn btn-success" data-toggle="modal" data-target="#myModalAccount" onclick="getValueAccount(' + element.id + ')">Update</button>' + '</td>' +
                    '<td>' + '<a class="btn btn-danger" onclick="deleteAccount(' + element.id + ')">Xóa</a>' + '</td>' +
                    '</tr>'
                )
            }
        }
    });
}

function pagingTableAccounts(totalPages) {
    var pageButton = "";
    // nút previous
    if (totalPages > 1 && pageNumber != 1) {
        pageButton += '<li class="page-item"><a class="page-link" onclick="previousPageAccount()">&laquo;</a></li>';
    }

    for (var i = 0; i < totalPages; i++) {
        pageButton += '<li class="page-item ' + (pageNumber == i + 1 ? "active" : "") + '">' +
            '<a class="page-link" onclick="changePageAccount(' + (i + 1) + ')">' + (i + 1) + '</a>' +
            '</li>';
    }

    //nút next trang
    if (totalPages > 1 && totalPages > pageNumber) {
        pageButton += '<li class="page-item"><a class="page-link" onclick="nextPageAccount()">&raquo;</a></li>';
    }
    $('#paginationDepartments').empty();
    $('#paginationDepartments').append(pageButton);
}

function changePageAccount(page) {
    if (page == pageNumber) {
        return;
    }
    pageNumber = page;
    loadNewDataAccount();
}

function previousPageAccount() {
    changePageAccount(pageNumber - 1)
}

function nextPageAccount() {
    changePageAccount(pageNumber + 1);
}

function loadNewDataAccount() {
    $('#GetAllAccounts').empty();
    GetAllAccounts();
}

function resetFormCreateAccount() {

}

function saveAccount() {
    var id = document.getElementById("id").value;
    console.log(id);
    if (id == null || id == "") {
        createAccount();
    } else {
        updateAccount();
    }
}

function createAccount() {
    //get data từ form frontend
    var username = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var departmentId = document.getElementById("departmentName").value;

    var account = {
        username: username,
        email: email,
        password: password,
        departmentId: departmentId,
    };

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/qlNhanVien/accounts",
        data: JSON.stringify(account), // body, truyền dữ liệu và body
        contentType: "application/json", // type of body, kiểu dữ liệu khi truyền vào body (json, xml, text)
        // dataType: 'json', // datatype return
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            // success
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            loadNewDataAccount();
        }
    })
}

function getValueAccount(id) {
    document.getElementById("formInputEmail").style.display = "none";
    document.getElementById("formInputPassword").style.display = "none";
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/qlNhanVien/accounts/" + id,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            document.getElementById("id").value = data.id;
            document.getElementById("username").value = data.username;
            document.getElementById("departmentName").value = data.departmentName;
            listDropdownDepartments(data.departmentId);
        }
    });
}

function updateAccount() {
    var id = document.getElementById("id").value;
    var username = document.getElementById("username").value;
    var departmentId = document.getElementById("departmentName").value;

    var account = {
        username: username,
        departmentId: departmentId,
    };


    console.log(account);
    $.ajax({
        type: "PUT",
        url: "http://localhost:8080/api/qlNhanVien/accounts/" + id,
        data: JSON.stringify(account), // body, truyền dữ liệu và body
        contentType: "application/json", // type of body, kiểu dữ liệu khi truyền vào body (json, xml, text)
        // dataType: 'json', // datatype return
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            // success
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            document.getElementById("resetformUpdateAccount").reset();
            loadNewDataAccount();
        }
    });
}

function listDropdownDepartments(departmentId) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/qlNhanVien/departments",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            console.log(data);

            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            getDataListDropdownDepartments(data.content, departmentId);
        }
    })
}

function getDataListDropdownDepartments(array, departmentId) {
    array.forEach(element => {
        if (departmentId == element.id) {
            $('#departmentName').append(
                '<option value="' + element.id + '" selected>' + element.name + '</option>'
            );
        }
        $('#departmentName').append(
            '<option value="' + element.id + '">' + element.name + '</option>'
        );
    });
}

function getDataListDropdownDepartmentsForUpdate(array) {
    array.forEach(element => {
        $('#departmentName').append(
            '<option value="' + element.id + '">' + element.name + '</option>'
        );
    });
}

function deleteAccount(id) {
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/api/qlNhanVien/accounts/" + id,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(localStorage.getItem("USERNAME") + ":" + localStorage.getItem("PASSWORD")));
        },
        success: function (data) {
            // success
            if (data == undefined || data == null) {
                alert("Load dữ liệu bị lỗi");
                return;
            }
            loadNewDataAccount();
        }
    });
}