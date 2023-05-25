export function doesHttpOnlyCookieExist(cookiename: string): boolean {
  var d: Date = new Date();
  d.setTime(d.getTime() + (1000));
  var expires = "expires=" + d.toUTCString();

  document.cookie = cookiename + "=new_value;path=/;" + expires;
  return document.cookie.indexOf(cookiename + '=') == -1;
}