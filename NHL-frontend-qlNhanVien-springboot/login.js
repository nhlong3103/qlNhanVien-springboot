function login() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    if (!username) {
        showNameError("Bạn chưa điền tên đăng nhập");
        return;
    }

    if (!password) {
        showNameError("Bạn chưa điền mật khẩu");
        return;
    }

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/qlNhanVien/auth/login",
        contentType: "application/json", // type of body, kiểu dữ liệu khi truyền vào body (json, xml, text)
        dataType: 'json', // datatype return
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(username + ":" + password));
        },
        success: function (data) {
            localStorage.setItem("ID", data.id);
            localStorage.setItem("USERNAME", username);
            localStorage.setItem("PASSWORD", password);
            localStorage.setItem("ROLE", data.role);

            window.location.replace("http://127.0.0.1:8081/home.html");
        },
        error(jqXHR) {
            if (jqXHR.status == 401) {
                showNameError("Đăng nhập thất bại, sai tài khoản hoặc mật khẩu")
            }
        }
    })
}

function showNameError(message) {
    document.getElementById("error").style.display = "block";
    document.getElementById("error").innerHTML = message;
}

function hideError() {
    document.getElementById("error").style.display = "none";
}