package com.qarantinno.api.domain;

import com.qarantinno.api.domain.execption.IllegalOperationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;

import static com.qarantinno.api.domain.execption.IllegalOperationException.IllegalOperationExceptionType.ILLEGAL_WEEK_DAY;

@Getter
@Setter
@NoArgsConstructor
public class Shot {

    @Getter
    private static final String UNNAMED = "unnamed";

    private Long id;
    private WeekDay weekDay;
    private Integer people;
    private Source source;
    private String trackingData;
    private OffsetDateTime shotAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Place place;

    public enum Source {
        google,
        yandex,
        web
    }

    @Getter
    public enum WeekDay {

        any(null, false),
        sun(DayOfWeek.SUNDAY, true),
        mon(DayOfWeek.MONDAY, true),
        tue(DayOfWeek.TUESDAY, true),
        wed(DayOfWeek.WEDNESDAY, true),
        thu(DayOfWeek.THURSDAY, true),
        fri(DayOfWeek.FRIDAY, true),
        sat(DayOfWeek.SATURDAY, true);

        private final DayOfWeek dayOfWeek;

        /**
         * Is indicates that day is plain day of week.
         * If not it is an option for search criteria
         */
        private final boolean plain;

        WeekDay(DayOfWeek dayOfWeek, boolean plain) {
            this.dayOfWeek = dayOfWeek;
            this.plain = plain;
        }

        public static WeekDay getFrom(DayOfWeek dayOfWeek) {
            return Arrays.stream(Shot.WeekDay.values())
                         .filter(weekDay -> weekDay.getDayOfWeek() != null && weekDay.getDayOfWeek().equals(dayOfWeek))
                         .findFirst()
                         .orElseThrow(() -> new IllegalOperationException(ILLEGAL_WEEK_DAY, "Unsupported week day"));
        }
    }
}
