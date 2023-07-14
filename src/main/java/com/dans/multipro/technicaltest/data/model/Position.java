package com.dans.multipro.technicaltest.data.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Position {
    private UUID id;
    private String type;
    private String url;
    @JsonAlias("created_at")
    private String createdAt;
    private String company;
    @JsonAlias("company_url")
    private String companyUrl;
    private String location;
    private String title;
    private String description;
    @JsonAlias("how_to_apply")
    private String howToApply;
    @JsonAlias("company_logo")
    private String companyLogo;

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", company='" + company + '\'' +
                ", companyUrl='" + companyUrl + '\'' +
                ", location='" + location + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", howToApply='" + howToApply + '\'' +
                ", companyLogo='" + companyLogo + '\'' +
                '}';
    }
}
