const url = new URL(window.location.href);
const srcParams = url.searchParams;
const id = srcParams.get('id');
let employee;

let totalExp = 0;
let total;

$.ajax({
    type: "GET",
    url: "http://localhost:8080/employee-ms/rest/employees/" + id,
    success: function (response) {
        employee = response;
        displayExperience(response.experiences);
        yearsInComp = response.yearsInCompany;
        total = totalExp + yearsInComp;
        displayEmployee(response);
        displayHoliday(response.holidays);
    }
})


const displayEmployee = (respEmployee) => {
    const employee = respEmployee;
    let content = ``;

    content += `<tr> <td> First Name: </td> <td> ` + employee.firstName + ` </td> </tr>`;
    content += `<tr> <td> Last Name: </td> <td> ` + employee.lastName + ` </td> </tr>`;
    content += `<tr> <td> Job Title: </td> <td>` + employee.jobTitle + `</td> </tr>`;
    content += `<tr> <td> Starting Date: </td> <td>` + employee.startDate + `</td> </tr>`;
    content += `<tr> <td> Years in Company: </td> <td>` + employee.yearsInCompany.toFixed(2) + `</td> </tr>`;
    content += `<tr> <th> Total experience: </th> <th>` + total.toFixed(2) + `</th> </tr>`;

    document.getElementById('employee-info').innerHTML = content;
} 

const displayExperience = (respExperiences) => {
    const experiences = respExperiences;
    let content = ``;
    experiences.forEach(experience => {
        content += `<tr> <td>` + experience.name + `</td> <td>` + experience.yearsWorked + `</td> </tr>`;
        totalExp += experience.yearsWorked;
    });

    content += `<tr> <th> Total: </th> <th>` + totalExp + `</th> </tr>`;
    document.getElementById('employee-experience').innerHTML += content;
}

const displayHoliday = (holidays) => {
    let content = ``;
    content += `<tr> <th> Days available: </th> <th>` + employee.vacationDays + `</th> </tr>`;
    content += `<tr> <th>Reason:</th> <th>Days off:</th> </tr>`;
    holidays.forEach(holiday => {
        content += `<tr> <td> ` + holiday.reason + ` </td> <td> ` + holiday.daysOff + ` </td> </tr>`
    });

    document.getElementById('employee-holiday').innerHTML += content;
}

const addHoliday = () => {
    const dateFrom = document.getElementById('hol-date-from').value;
    const dateTo = document.getElementById('hol-date-to').value;
    const reason = document.getElementById('hol-reason').value;

    let daysOff = dateDiffInDays(dateFrom, dateTo);

    if ( daysOff < 0 || ((employee.vacationDays - daysOff) < 0)) {
        alert("You can't go on vacation, no free days!");
        return;
    }

    if (daysOff === 0) {
        daysOff = 1;
    }

    employee.holidays.push({
        "reason": reason,
        "dateFrom": dateFrom,
        "dateTo": dateTo
    });

    const emp = {
        "id": id,
        "firstName": employee.firstName,
        "lastName": employee.lastName,
        "jobTitle": employee.jobTitle,
        "startDate": employee.startDate,
        "vacationDays": employee.vacationDays,
        "experiences": employee.experiences,
        "holidays": employee.holidays
    }

    console.log(emp);

    $.ajax({
        type: "PUT",
        url: "http://localhost:8080/employee-ms/rest/employees",
        data: JSON.stringify(emp),
        contentType: "application/json",
        success: function (response) {
            window.location.href = "http://127.0.0.1:8080/employee-ms/info.html?id=" + id;
        }
    });
}

const dateDiffInDays = (dateFrom, dateTo) => {
    const a = new Date(dateFrom);
    const b = new Date(dateTo);
    const _MS_PER_DAY = 1000 * 60 * 60 * 24;

    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
  
    return Math.floor((utc2 - utc1) / _MS_PER_DAY);
  }