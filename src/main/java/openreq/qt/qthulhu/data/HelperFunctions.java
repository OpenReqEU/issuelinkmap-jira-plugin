package openreq.qt.qthulhu.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class HelperFunctions
{

    private HelperFunctions ()
    {

    }

    /**
     * Builds a unique id based on the issue key.
     * These can have up to 10 digits and are probably going into 11 digits soon, therefore we are using long instead of int
     * @param key the key, ex. QTWB-10
     * @return a unique long to identify an issue for vis.js
     */
    public static long calculateUniqueID(String key)
    {
        int n = key.indexOf('-');

        String project = key.substring(0, n);
        int projectId = ProjectIDs.getProjectID(project);

        //we are using regex here because UH's services sometimes mock issues and then the key is funny
        String stringNodeId = key.substring(n + 1).replaceAll("[^0-9]", "");
        stringNodeId = projectId + stringNodeId;
        return Long.parseLong(stringNodeId);
    }

    /**
     * This method cleans strings (description, comment, summary, title, etc.) of
     *
     * @param issueDesc the text that should be cleaned
     * @return the cleaned text
     */
    public static String cleanText(JsonElement issueDesc)
    {
        if (issueDesc == null)
        {
            return "none";
        }
        if (!issueDesc.equals(JsonNull.INSTANCE))
        {
            //TODO: This can be done easier, I assume
            String text = issueDesc.getAsString().replaceAll("[\\r\\n]+", "");
            text = text.replace("'", "");
            text = text.replace("\r\n", "");
            text = text.replace("\r", "");
            text = text.replace("\n", "");
            text = text.replace("\"", "");
            text = text.replace("?", "? ");
            text = text.replace("!", "! ");
            text = text.replace(".", ". ");
            text = text.replace("\\", "");
            text = text.replace("[", "");
            text = text.replace("]", "");
            return text;
        }
        //sometimes there are nulls where there should be some text for the front end
        else
        {
            return "none";
        }
    }

    public static JsonArray fillParts(JsonArray parts, String fillPlaceholder)
    {
        JsonObject resolution = new JsonObject();
        resolution.addProperty("name", "Resolution");
        resolution.addProperty("text", fillPlaceholder);
        parts.add(resolution);
        JsonObject platforms = new JsonObject();
        platforms.addProperty("name", "Platforms");
        platforms.addProperty("text", fillPlaceholder);
        parts.add(platforms);
        JsonObject versions = new JsonObject();
        versions.addProperty("name", "Versions");
        versions.addProperty("text", fillPlaceholder);
        parts.add(versions);
        JsonObject labels = new JsonObject();
        labels.addProperty("name", "Labels");
        labels.addProperty("text", fillPlaceholder);
        parts.add(labels);
        JsonObject environment = new JsonObject();
        environment.addProperty("name", "Environment");
        environment.addProperty("text", fillPlaceholder);
        parts.add(environment);
        JsonObject status = new JsonObject();
        status.addProperty("name", "Status");
        status.addProperty("text", fillPlaceholder);
        parts.add(status);
        JsonObject fixVersion = new JsonObject();
        fixVersion.addProperty("name", "FixVersion");
        fixVersion.addProperty("text", fillPlaceholder);
        parts.add(fixVersion);
        JsonObject components = new JsonObject();
        components.addProperty("name", "Components");
        components.addProperty("text", fillPlaceholder);
        parts.add(components);

        return parts;
    }
}
