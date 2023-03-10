package com.synergysuite.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Holiday {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate dateFrom;
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate dateTo;
    private String reason;
    private Integer daysOff;

    public Holiday() {}

    public Holiday(LocalDate dateFrom, LocalDate dateTo, String reason) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(Integer daysOff) {
        this.daysOff = daysOff;
    }

    @PrePersist
    @PreUpdate
    public void calculateDaysOff() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = df.parse(dateFrom.toString());
        Date date2 = df.parse(dateTo.toString());
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        int numberOfDays = 1;
        while (cal1.before(cal2)) {
            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                    &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
                numberOfDays++;
            }
            cal1.add(Calendar.DATE,1);
        }

        daysOff = numberOfDays;
    }
}
