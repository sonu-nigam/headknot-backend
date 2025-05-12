import { Editor, generateJSON } from 'https://cdn.jsdelivr.net/npm/@tiptap/core@2.2.2/+esm'
import Document from 'https://cdn.jsdelivr.net/npm/@tiptap/extension-document@2.2.1/+esm'
import Placeholder from 'https://cdn.jsdelivr.net/npm/@tiptap/extension-placeholder@2.2.1/+esm'
import Heading from 'https://cdn.jsdelivr.net/npm/@tiptap/extension-heading@2.2.1/+esm'
import Paragraph from 'https://cdn.jsdelivr.net/npm/@tiptap/extension-paragraph@2.2.1/+esm'
import Text from 'https://cdn.jsdelivr.net/npm/@tiptap/extension-text@2.2.1/+esm'
import Bold from 'https://cdn.jsdelivr.net/npm/@tiptap/extension-bold@2.2.1/+esm'

class RichTextArea extends HTMLElement {
    static formAssociated = true;
    #internals;

    constructor() {
        super();
        this.editor = null;
        this.#internals = this.attachInternals();
        this.name = this.getAttribute("name");
    }

    initializeEditor() {
        const Title = Heading.extend({
            name: "title",
            group: "none",
        }).configure({ levels: [1] });

        const BodyHeading = Heading.extend({
            name: "h23456",
        }).configure({ levels: [2, 3, 4, 5, 6] });

        const CustomDocument = Document.extend({
            content: 'title block*'
        });

        const initialContent = this.innerHTML;
        this.innerHTML = ""
        this.editor = new Editor({
            extensions: [
                CustomDocument,
                Title,
                BodyHeading,
                Paragraph,
                Text,
                Bold,
                Placeholder.configure({
                    emptyNodeClass: "empty",
                    placeholder: ({ node }) => {
                        if (node.type.name === "title") {
                            return "Task title..."
                        }

                        // return "Can you add some further content?"
                    }
                })
            ],
            content: initialContent,
            element: this,
            editorProps: {
                attributes: {
                    class: 'prose prose-sm sm:prose lg:prose-lg xl:prose-2xl mx-auto focus:outline-none',
                },
            },
            onUpdate: ({ editor }) => {
                const json = editor.getJSON()
                const html = editor.getHTML()
                this.#internals.setFormValue(this.name, json);
                this.updatValidity();
            },
        })
    }

    getTitle(editor) {
        const { state } = editor;
        const hasH1Tag = state.doc.descendants((node) => node.type === 'heading' && node.attrs.level === 1);
        return hasH1Tag;
    }

    updatValidity() {
        console.log(this.editor.state.doc)
        if (this.getTitle(this.editor).textContent.trim()) {
            console.log("No Title Available")
        }
        else {
            console.log("Title is available")
        }
    }
    
    destroyEditor() {
        this.editor.destroy();
        this.editor = null;
    }

    connectedCallback() {
        this.initializeEditor()
    }

    disconnectedCallback() {
        this.destroyEditor()
    }

    attributeChangedCallback(name, oldvalue, newValue) {
        this.#internals.setFormValue(newValue);
    }
}

window.customElements.define('rich-textarea', RichTextArea)
