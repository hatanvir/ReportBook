package com.example.tanvir.reportbook.Models;

public class ReportDetails {
    private String id;
    private String prayer;
    private String quran;
    private String hadith;
    private String academicBook;
    private String novelOrOtherBook;
    private String newspaper;
    private String skillDev;
    private String marketing;
    private String wakeupTime;
    private String sleepTime;
    private String workout;

    public ReportDetails(String id, String prayer, String quran,
                         String hadith, String academicBook, String novelOrOtherBook,
                         String newspaper, String skillDev, String marketing, String wakeupTime,
                         String sleepTime, String workout) {
        this.id = id;
        this.prayer = prayer;
        this.quran = quran;
        this.hadith = hadith;
        this.academicBook = academicBook;
        this.novelOrOtherBook = novelOrOtherBook;
        this.newspaper = newspaper;
        this.skillDev = skillDev;
        this.marketing = marketing;
        this.wakeupTime = wakeupTime;
        this.sleepTime = sleepTime;
        this.workout = workout;
    }

    public String getId() {
        return id;
    }

    public String getPrayer() {
        return prayer;
    }

    public String getQuran() {
        return quran;
    }

    public String getHadith() {
        return hadith;
    }

    public String getAcademicBook() {
        return academicBook;
    }

    public String getNovelOrOtherBook() {
        return novelOrOtherBook;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public String getSkillDev() {
        return skillDev;
    }

    public String getMarketing() {
        return marketing;
    }

    public String getWakeupTime() {
        return wakeupTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public String getWorkout() {
        return workout;
    }
}
