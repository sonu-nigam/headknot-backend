package org.workflow.Tasks.Project;

import java.util.ArrayList;
import java.util.List;

import org.workflow.Shared.Record.NavLinkRecord;

public class ProjectUtils {
    public static List<NavLinkRecord> getProjectNavlinks(String projectSlug, String currentUrl) {
        List<NavLinkRecord> navLinks = new ArrayList<>();
        String commonUrlString = "/projects/" + projectSlug;
        String summaryUrl = commonUrlString + "/overview";
        String tasksUrl = commonUrlString;
        String docsUrl = commonUrlString + "/docs";
        String settingsUrl = commonUrlString + "/settings";

        navLinks.add(createNavLink(summaryUrl, "Overview", currentUrl));
        navLinks.add(createNavLink(tasksUrl, "Tasks", currentUrl));
        navLinks.add(createNavLink(docsUrl, "Docs", currentUrl));
        navLinks.add(createNavLink(settingsUrl, "Forms", currentUrl));
        navLinks.add(createNavLink(settingsUrl, "Backlogs", currentUrl));
        navLinks.add(createNavLink(settingsUrl, "Timeline", currentUrl));
        navLinks.add(createNavLink(settingsUrl, "Settings", currentUrl));
        return navLinks;
    }

    public static NavLinkRecord createNavLink(String url, String title, String currentUrl) {
        if (currentUrl.equals(url)) {
            return new NavLinkRecord(url, true, title);
        }
        return new NavLinkRecord(url, false, title);
    }
}
