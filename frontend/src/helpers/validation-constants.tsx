export const regexUsername: RegExp = new RegExp("[A-Za-z][A-Za-z0-9_]{4,29}");
export const regexPassword: RegExp = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{7,30})");
export const regexEmail: RegExp = new RegExp("^(.+)@(.+)$");