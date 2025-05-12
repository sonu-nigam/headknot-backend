class ToggleButton extends HTMLElement {
    constructor() {
        super();
    }

    get isSelected() {
        return !!this.getAttribute("selected");
    }
    handleClick() {
        const name = this.getAttribute("name");
        if (name) {
            const toggleButtonList = document.querySelectorAll(
                `toggle-button[name="${name}"]`,
            );
            toggleButtonList.forEach((btn) => {
                btn.removeAttribute("selected");
            });
        }
        this.setAttribute("selected", "");
    }
    connectedCallback() {
        this.addEventListener("click", this.handleClick);
    }
    disconnectedCallback() {
        this.removeEventListener("click", this.handleClick);
    }
}

window.customElements.define("toggle-button", ToggleButton);
