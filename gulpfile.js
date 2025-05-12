import { dirname } from "path";
import { fileURLToPath } from "url";
import tap from "gulp-tap";
import posthtml from "gulp-posthtml";
import { task, src, dest } from "gulp";
import posthtmlInclude from "posthtml-include";
import posthtmlExtend from "posthtml-extend";
import spaceless from "posthtml-spaceless";
import postcss from "posthtml-postcss";
import tailwindcss from "tailwindcss";
import autoprefixer from "autoprefixer";

const postcssPlugins = [tailwindcss(), autoprefixer()];
const postcssOptions = {};
const filterType = /^text\/css$/;

task("html", () => {
    const __filename = fileURLToPath(import.meta.url);
    const __dirname = dirname(__filename);

    const javaPlugin = function () {
        function PLUGIN_NAME_CAMEL(tree) {
            // Your plugin
            console.log(tree);
            return tree;
        }

        return PLUGIN_NAME_CAMEL;
    };

    const plugins = [
        posthtmlInclude({ root: __dirname + "/src/main/frontend/" }),
        posthtmlExtend({ root: __dirname + "/src/main/frontend/" }),
        spaceless(),
        // javaPlugin(),
        postcss(postcssPlugins, postcssOptions, filterType),
    ];
    const options = {
        directives: [
            { name: "section-start", start: "{{#", end: "}}" },
            { name: "section-end", start: "{{/", end: "}}" },
            { name: "partial-start", start: "{{>", end: "}}" },
            { name: "parent-start", start: "{{<", end: "}}" },
        ],
    };

    return src("**/*.html", {
        root: __dirname + "/src/main/frontend/",
        cwd: __dirname + "/src/main/frontend/",
    })
        .pipe(tap((file) => file.path))
        .pipe(posthtml(plugins, options))
        .pipe(dest(__dirname + "/src/main/resources/templates"));
});
