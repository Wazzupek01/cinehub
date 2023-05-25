export function genCommaDividedList(list: string[]): string {
  let str: string = "";
  for (let i: number = 0; i < list.length; i++) {
    if (i !== list.length - 1) {
      str += `${list[i]}, `;
    } else {
      str += list[i];
    }
  }
  return str;
}