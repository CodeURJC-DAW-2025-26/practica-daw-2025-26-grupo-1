package es.codeurjc.daw.museum.dto;

import java.util.List;

public record SectionDTO (
    String nameSection,
    String iconUrl,
    String link,
    List <String> categories
) {}
