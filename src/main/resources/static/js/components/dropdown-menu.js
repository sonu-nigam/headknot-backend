class DropdownMenu extends HTMLElement {
    constructor() {
        super();
    }

    hide() {
        this.style.display = "none";
    }

    show() {
        this.style.display = "block";
    }

    connectedCallback() {
        this.addEventListener("focus", () => {
            console.log("focus");
        });
        this.addEventListener("click", () => {
            console.log("click");
        });
    }

    disconnectedCallback() {}
}

window.customElements.define("dropdown-menu", DropdownMenu);

class DropdownValue extends HTMLElement {
    constructor() {
        super();
    }

    hide() {
        this.style.display = "none";
    }

    show() {
        this.style.display = "block";
    }

    handleMenuVisibility() {
        const dropdownMenu = this.closest("dropdown-menu");
        console.log(dropdownMenu);
    }

    connectedCallback() {
        this.addEventListener("click", this.handleMenuVisibility);
    }

    disconnectedCallback() {}
}

window.customElements.define("dropdown-value", DropdownValue);
