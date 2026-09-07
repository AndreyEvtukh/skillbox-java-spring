package com.diploma.skillboxjavaspring.specification;

import com.diploma.skillboxjavaspring.dto.hotels.HotelFilterDTO;
import com.diploma.skillboxjavaspring.entity.Hotel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Provides JPA specifications for filtering hotels.
 */
public final class HotelSpecification {

    /**
     * Prevents instantiation of this utility class.
     */
    private HotelSpecification() {
    }

    /**
     * Builds a specification from the supplied hotel filter.
     *
     * @param filter the optional hotel filtering criteria
     * @return a specification containing the applicable filter predicates
     */
    public static Specification<Hotel> filter(HotelFilterDTO filter) {

        return (root, query, cb) -> {

            if (filter == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (hasText(filter.name())) {
                predicates.add(cb.like(cb.lower(root.get("name")), containsIgnoreCase(filter.name())));
            }

            if (hasText(filter.title())) {
                predicates.add(cb.like(cb.lower(root.get("title")), containsIgnoreCase(filter.title())));
            }

            if (hasText(filter.city())) {
                predicates.add(cb.like(cb.lower(root.get("city")), containsIgnoreCase(filter.city())));
            }

            if (hasText(filter.address())) {
                predicates.add(cb.like(cb.lower(root.get("address")), containsIgnoreCase(filter.address())));
            }

            if (filter.distanceToCenter() != null) {
                predicates.add(cb.equal(root.get("distanceToCenter"), filter.distanceToCenter()));
            }

            if (filter.rating() != null) {
                predicates.add(cb.equal(root.get("rating"), filter.rating()));
            }

            if (filter.numOfRating() != null) {
                predicates.add(cb.equal(root.get("numOfRating"), filter.numOfRating()));
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

    /**
     * Creates a case-insensitive SQL {@code LIKE} pattern for a value.
     *
     * @param value the value to convert into a pattern
     * @return a trimmed, lower-case value wrapped in wildcard characters
     */
    private static String containsIgnoreCase(String value) {
        return "%" + value.trim().toLowerCase(Locale.ROOT) + "%";
    }
}
