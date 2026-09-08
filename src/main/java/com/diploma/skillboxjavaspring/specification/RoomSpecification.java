package com.diploma.skillboxjavaspring.specification;

import com.diploma.skillboxjavaspring.dto.room.RoomFilterDTO;
import com.diploma.skillboxjavaspring.entity.Booking;
import com.diploma.skillboxjavaspring.entity.Hotel;
import com.diploma.skillboxjavaspring.entity.Room;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Provides JPA specifications for filtering rooms and checking availability.
 */
@Slf4j
public final class RoomSpecification {

    /**
     * Prevents instantiation of this utility class.
     */
    private RoomSpecification() {
    }

    /**
     * Builds a specification from the supplied room filter.
     *
     * @param filter the optional room filtering criteria
     * @return a specification containing the applicable filter predicates
     */
    public static Specification<Room> filter(RoomFilterDTO filter) {

        return (Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            if (filter == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (hasText(filter.name())) {
                predicates.add(
                        cb.equal(
                                cb.lower(root.get("name")),
                                filter.name().trim().toLowerCase(Locale.ROOT)
                        )
                );
            }

            if (filter.number() != null) {
                predicates.add(
                        cb.equal(root.get("number"), filter.number())
                );
            }

            if (filter.minPrice() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("price"), filter.minPrice())
                );
            }

            if (filter.maxPrice() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("price"), filter.maxPrice())
                );
            }

            if (filter.maxCapacity() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("maxCapacity"), filter.maxCapacity())
                );
            }

            if (filter.hotelIds() != null && !filter.hotelIds().isEmpty()) {
                predicates.add(
                        root.<Hotel>get("hotel").get("id").in(filter.hotelIds())
                );
            }

            if (filter.bookingDates() != null && filter.bookingDates().size() == 2) {

                LocalDate checkIn = filter.bookingDates().get(0);
                LocalDate checkOut = filter.bookingDates().get(1);
                log.debug(
                        "Filtering rooms by booking dates: checkIn={}, checkOut={}",
                        checkIn,
                        checkOut
                );
                Subquery<UUID> subquery = query.subquery(UUID.class);
                Root<Booking> booking = subquery.from(Booking.class);

                subquery.select(booking.get("id"));

                subquery.where(
                        cb.equal(
                                booking.get("room").get("id"),
                                root.get("id")
                        ),
                        cb.lessThan(
                                booking.get("checkIn"),
                                checkOut
                        ),
                        cb.greaterThan(
                                booking.get("checkOut"),
                                checkIn
                        )
                );

                predicates.add(
                        cb.not(cb.exists(subquery))
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Checks whether a value contains non-whitespace text.
     *
     * @param value the value to check
     * @return {@code true} when the value is not null and contains text
     */
    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
