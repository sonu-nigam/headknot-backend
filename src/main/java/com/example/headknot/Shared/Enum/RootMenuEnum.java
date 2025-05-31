package com.example.headknot.Shared.Enum;

public enum RootMenuEnum {
    APP("App", "apps", "/"),
    NOTES("Notes", "notes"),
    TASKS("Tasks", "checkbox"),
    CALENDAR("Calendar", "calendar"),
    CONNECT("Connect", "message"),
    FILES("Files", "file"),
    MAIL("Mail", "mail"),
    HR("HR", "users"),
    SETTINGS("Settings", "adjustments"),
    HELP("Help", "help");

    public final String value;
    public final String icon;
    public final String url;

    RootMenuEnum(String value, String icon, String url) {
        this.value = value;
        this.icon = icon;
        this.url = url;
    }

    RootMenuEnum(String value, String icon) {
        this.value = value;
        this.icon = icon;
        this.url = value;
    }

}
