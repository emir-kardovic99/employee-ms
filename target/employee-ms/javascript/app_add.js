const addEmployee = () => {
    let fName = document.getElementById("first_name").value;
    let lName = document.getElementById("last_name").value;
    let jobTitle = document.getElementById("job_title").value;
    let startDate = document.getElementById("start_date").value;
    let expName = document.getElementsByClassName("exp_name");
    let expDateFrom = document.getElementsByClassName("exp_date_from");
    let expDateTo = document.getElementsByClassName("exp_date_to");

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

    let jsonData = {
        "firstName": fName,
        "lastName": lName,
        "jobTitle": jobTitle,
        "startDate": startDate,
        "experiences": experiences,
        "holidays": []
    }

    $.ajax({
        url: 'http://localhost:8080/employee-ms/rest/employees',
        type: 'POST',
        data: JSON.stringify(jsonData),
        contentType: 'application/json',
        success: (response) => {
            window.location.href = "http://127.0.0.1:8080/employee-ms/index.html";
        },
        error: (error) => {
            console.log("ERROR!");
        }
    })
}

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

const dateDiffInDays = (dateFrom, dateTo) => {
    const a = new Date(dateFrom);
    const b = new Date(dateTo);
    const _MS_PER_DAY = 1000 * 60 * 60 * 24;

    const utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
    const utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
  
    return Math.floor((utc2 - utc1) / _MS_PER_DAY);
}