class TabPanel extends HTMLElement {
    constructor() {
        super();
    }

    hide() {
        this.style.display = "none";
    }

    show() {
        this.style.display = "block";
    }

    connectedCallback() {}

    disconnectedCallback() {}
}

window.customElements.define("tab-panel", TabPanel);
