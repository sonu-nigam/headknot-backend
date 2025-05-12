class TabContext extends HTMLElement {
    constructor() {
        super();
    }

    handleTabPanel(control) {
        const currentPanel = control ? this.querySelector(`#${control}`) : null;

        this.querySelectorAll("[role='tabpanel']").forEach((panel) => {
            if (panel === currentPanel) panel.style.display = "block";
            else panel.style.display = "none";
        });
    }

    handleClick(event) {
        if (event.target.tagName == "TOGGLE-BUTTON") {
            const control = event.target.attributes.control.nodeValue || null;
            this.handleTabPanel(control);
        }
    }

    connectedCallback() {
        this.addEventListener("click", this.handleClick);

        const selectedTab = this.querySelector("toggle-button[selected]");
        if (selectedTab) {
            const control = selectedTab.attributes.control.nodeValue || null;
            this.handleTabPanel(control);
        }
    }

    disconnectedCallback() {
        this.removeEventListener("click", this.handleClick);
    }
}

window.customElements.define("tab-context", TabContext);
