import {
    flip,
    shift,
    offset,
    computePosition,
} from "https://cdn.jsdelivr.net/npm/@floating-ui/dom@1.6.12/+esm";

class PopoverBox extends HTMLElement {
    constructor() {
        super();
        // this.style.setProperty("display", "none");
    }

    get controlElement() {
        const controlElementId = this.getAttribute("control");
        const controlElement = document.querySelector("#" + controlElementId);
        return controlElement;
    }

    update() {
        const controlElement = this.controlElement;
        const controlElementWidth =
            controlElement.getBoundingClientRect().width;

        computePosition(controlElement, this, {
            placement: "bottom-start",
            middleware: [offset(6), flip(), shift({ padding: 5 })],
        }).then(({ x, y, placement, middlewareData }) => {
            Object.assign(this.style, {
                position: "absolute",
                left: `${x}px`,
                top: `${y}px`,
            });
        });
    }

    showTooltip = () => {
        this.style.display = "block";
        this.update();
        this.setAttribute("open", "");
    };

    hideTooltip = () => {
        this.style.display = "";
        this.removeAttribute("open");
    };

    connectedCallback() {
        const controlElement = this.controlElement;
        const open = this.hasAttribute("open");
        if (open) {
            this.showTooltip();
        }

        [
            ["focus", this.showTooltip],
            ["blur", this.hideTooltip],
        ].forEach(([event, listener]) => {
            controlElement.addEventListener(event, listener);
        });
    }

    disconnectedCallback() {
        [
            ["focus", this.showTooltip],
            ["blur", this.hideTooltip],
        ].forEach(([event, listener]) => {
            controlElement.removeEventListener(event, listener);
        });
    }
}

window.customElements.define("popover-box", PopoverBox);
