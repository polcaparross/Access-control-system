package basenostates;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *Schedule class that has times and days.
 *We need the class for the most time aspects.
 */

public class Schedule {
    private LocalDate dateInici;
    private LocalDate dateFin;
    private LocalTime timeInici;
    private LocalTime timeFin;
    private ArrayList<DayOfWeek> days;


    public Schedule(final LocalDate dateInitial, final LocalDate dateFinal,
                    final ArrayList<DayOfWeek> daysWeek,
                    final LocalTime timeInit, final LocalTime timeFinal) {
        this.dateInici = dateInitial;
        this.dateFin = dateFinal;
        this.days = daysWeek;
        this.timeInici = timeInit;
        this.timeFin = timeFinal;
    }

    //we use this function to know if
    // we are between correct hours
    // to confirm that
    //we are in the correct schedule,
    // we use that for the user's schedules.
    //returns a boolean.
    public boolean isSchedule(final LocalDateTime now) {
        boolean daysTrue = days.contains(now.getDayOfWeek());
        boolean dateTrue = now.toLocalDate().isAfter(dateInici)
                && now.toLocalDate().isBefore(dateFin);
        boolean timeTrue = now.toLocalTime().isAfter(timeInici)
                && now.toLocalTime().isBefore(timeFin);

        return daysTrue && dateTrue && timeTrue;
    }
    // all getters
    public LocalDate getDateInici() {
        return dateInici;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public LocalTime getTimeInici() {
        return timeInici;
    }

    public LocalTime getTimeFin() {
        return timeFin;
    }

    public ArrayList<DayOfWeek> getDays() {
        return days;
    }
}
