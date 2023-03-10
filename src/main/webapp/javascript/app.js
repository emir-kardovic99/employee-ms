let employees;

document.addEventListener("DOMContentLoaded", () => {
    listEmployees();
  });

const listEmployees = () => {
    $.ajax({
        url: 'http://localhost:8080/employee-ms/rest/employees?name=',
        type: 'GET',
        success: (response) => {
            addContentInTable(response);
        }
    });
}

const findByName = () => {
    let fName = document.getElementById("src_name").value;
    let url = 'http://localhost:8080/employee-ms/rest/employees?name=' + fName;

    $.ajax({
        url:  url,
        type: 'GET',
        success: (response) => {
            addContentInTable(response);
        }
    });
}

const addContentInTable = (response) => {
    employees = response;

    var content = ``;
    response.forEach(element => {
        console.log(element);
        content += `<tr> <td>` + element.firstName + `</td>` +
                        `<td>` + element.lastName + `</td>` +
                        `<td>` + element.jobTitle + `</td>` +
                        `<td>` + element.startDate + `</td>` +
                        `<td>` + element.yearsInCompany.toFixed(2) + `</td>` +
                        `<td> <a class='btn btn-primary' href='http://127.0.0.1:8080/employee-ms/info.html?id=` + element.id + `'>Info</a> </td>` +
                        `<td> <a class='btn btn-primary' href='http://127.0.0.1:8080/employee-ms/edit.html?id=` + element.id + `'>Edit</a> </td>` +
                        `<td> <a class='btn btn-danger' onclick='deleteEmployee(` + element.id + `)'>Delete</a></td></tr>`;
    });

    document.getElementById("main-table").innerHTML = content;
}

const deleteEmployee = (id) => {
    if (!confirm("Are you sure you want to delete Employee?")) {
        return;
    }

    let employee;

    employees.forEach(element => {
        if (element.id === id) {
            employee = element;
        }
    });

    $.ajax({
        url: 'http://localhost:8080/employee-ms/rest/employees/',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(employee),
        success: (response) => {
            listEmployees();
        }
    });
}
