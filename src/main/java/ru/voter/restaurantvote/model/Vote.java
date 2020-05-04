package ru.voter.restaurantvote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "votes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vote extends AbstractBaseEntity {

    @NotNull
    @Column(name = "vote_date", nullable = false, unique = true, columnDefinition = "2020-05-01")
    private LocalDate voteDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
