const url = new URL(window.location.href);
const srcParams = url.searchParams;
const id = srcParams.get('id');

document.addEventListener("DOMContentLoaded", () => {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/employee-ms/rest/employees/" + id,
        success: function (response) {
            document.getElementById('first_name').value = response.firstName;
            document.getElementById('last_name').value = response.lastName;
            document.getElementById('job_title').value = response.jobTitle;
            document.getElementById('start_date').value = response.startDate;
            document.getElementById('vacation_days').value = response.vacationDays;
    
            const experiences = response.experiences;
            let content = ``;
            experiences.forEach(experience => {
                content += `
                <hr>
                <div class="inp-wrapper">
                    <label for="exp_name">Company name</label>
                    <input type="text" class="form-control exp_name" id="exp_name" maxlength="20" value='` + experience.name +`'>
                </div>
                <div class="inp-date inp-wrapper">
                    <div>
                        <label for="exp_date_from">From:</label>
                        <input type="date" class="form-control exp_date_from" id="exp_date_from" value='` + experience.dateFrom +`'>
                    </div>
                    <div>
                        <label for="exp_date_to">To:</label>
                        <input type="date" class="form-control exp_date_to" id="exp_date_to" value='` + experience.dateTo + `'>
                    </div>
                </div>`;
                
            });
            document.getElementById('experiences-div').innerHTML = content;
    
            const holidays = response.holidays;
            content = ``;
            holidays.forEach(holiday => {
                content += `
                    <input type="hidden" class="hol_reason" value='` + holiday.reason + `'>
                    <input type="hidden" class="hol_date_from" value='` + holiday.dateFrom + `'>
                    <input type="hidden" class="hol_date_to" value='` + holiday.dateTo + `'>
                    <input type="hidden" class="hol_days_off" value='` + holiday.daysOff + `'>
                `
            });
    
            document.getElementById('experiences-div').innerHTML += content;
        }
    });
});

const addExperience = () => {
    content = `
    <hr>
    <div class="inp-wrapper">
        <label for="exp_name">Company name</label>
        <input type="text" class="form-control exp_name" id="exp_name" maxlength="20">
    </div>
    <div class="inp-date inp-wrapper">
        <div>
            <label for="exp_date_from">From:</label>
            <input type="date" class="form-control exp_date_from" id="exp_date_from">
        </div>
        <div>
            <label for="exp_date_to">To:</label>
            <input type="date" class="form-control exp_date_to" id="exp_date_to">
        </div>
    </div>`;

    document.getElementById('experiences-div').innerHTML += content;
}

const editEmployee = () => {
    let fName = document.getElementById("first_name").value;
    let lName = document.getElementById("last_name").value;
    let jobTitle = document.getElementById("job_title").value;
    let startDate = document.getElementById("start_date").value;
    let expName = document.getElementsByClassName("exp_name");
    let expDateFrom = document.getElementsByClassName("exp_date_from");
    let expDateTo = document.getElementsByClassName("exp_date_to");
    let vacationDays = document.getElementById("vacation_days").value;
    let holReason = document.getElementsByClassName("hol_reason");
    let holDateFrom = document.getElementsByClassName("hol_date_from");
    let holDateTo = document.getElementsByClassName("hol_date_to");
    let holDaysOff = document.getElementsByClassName("hol_days_off");

    let experiences = [];
    for (let i=0; i < expName.length; i++) {
        if (dateDiffInDays(expDateFrom[i].value, expDateTo[i].value) < 0) {
            alert("Dates must be in order!");
            return;
        }

        experiences.push({
            "name": expName[i].value,
            "dateFrom": expDateFrom[i].value,
            "dateTo": expDateTo[i].value
        })
    }

    let holidays = [];
    for (let i = 0; i < holReason.length; i++) {
        holidays.push({
            "reason": holReason[i].value,
            "dateFrom": holDateFrom[i].value,
            "dateTo": holDateTo[i].value,
            "daysOff": holDaysOff[i].value
        })
    }

    const employee = {
        "id": id,
        "firstName": fName,
        "lastName": lName,
        "jobTitle": jobTitle,
        "startDate": startDate,
        "vacationDays": vacationDays,
        "experiences": experiences,
        "holidays": holidays
    }

    $.ajax({
        type: "PUT",
        url: "http://localhost:8080/employee-ms/rest/employees",
        data: JSON.stringify(employee),
        contentType: "application/json",
        success: function (response) {
            window.location.href = "http://127.0.0.1:8080/employee-ms/index.html";
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