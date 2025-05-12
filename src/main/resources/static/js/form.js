function onchangeCombobox(event, curEle) {
    const targetElement = curEle.querySelector("data");
    curEle.value = event.target.value;
    targetElement.innerText = event.target.value;
}

function onLoaded(event) {
    const srcElement = event.target;
    const fieldset = srcElement.closest("fieldset");
    const target = fieldset.querySelector("data");

    if (event.newState === "open") {
        target.classList.add("btn-active");
    } else {
        target.classList.remove("btn-active");
    }
}

function onSubmit(event) {
    event.preventDefault();
    const srcElement = event.target;
    const fieldset = srcElement.closest("fieldset");
    const target = fieldset.querySelector("data");

    if (event.newState === "open") {
        target.classList.add("btn-active");
    } else {
        target.classList.remove("btn-active");
    }
}

document.querySelectorAll("[popover]").forEach((element) => {
    element.addEventListener("toggle", onLoaded);
    element.addEventListener("submit", onSubmit);
});

document.body.addEventListener("htmx:beforSwap", (event) => {
    const newElements = event.detail.elt.querySelectorAll("[popover]");
    newElements.forEach((element) => {
        element.removeEventListener("toggle", onLoaded);
        element.removeEventListener("submit", onSubmit);
    });
});

document.body.addEventListener("htmx:afterSwap", (event) => {
    const newElements = event.detail.elt.querySelectorAll("[popover]");
    newElements.forEach((element) => {
        element.addEventListener("toggle", onLoaded);
        element.addEventListener("submit", onSubmit);
    });
});
