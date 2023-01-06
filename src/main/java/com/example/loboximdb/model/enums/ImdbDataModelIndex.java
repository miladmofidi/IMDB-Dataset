package com.example.loboximdb.model.enums;

/**
 * @author milad.mofidi@gmail.com
 */public enum ImdbDataModelIndex
{
    NCONST("nconst"),
    PRIMARYNAME("primaryName"),
    BIRTHYEAR("birthYear"),
    DEATHYEAR("deathYear"),
    PRIMARYPROFESSION("primaryProfession"),
    KNOWNFORTITLES("knownForTitles");

    public final String label;


    ImdbDataModelIndex(String label)
    {
        this.label = label;
    }

    public static ImdbDataModelIndex valueOfLabel(String label) {
        for (ImdbDataModelIndex e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
