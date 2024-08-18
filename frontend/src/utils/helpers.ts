import { monthNames } from "./constants";

export function isValidEmail(email: string): boolean {
  const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  return pattern.test(email);
}

export function isValidPhoneNumber(phoneNumber: string): boolean {
  const pattern = /^[0-9]{10}$/;
  return pattern.test(phoneNumber);
}

export function isValidateDate(month: number, day: number, year: number): boolean {
  const date = new Date(year, month, day);

  return date.getFullYear() === year &&
    date.getMonth() === month &&
    date.getDate() === day;
}

export function dateDifferenceToString(from: Date, to: Date): string {
  from = new Date(from)
  const diff = Math.abs(to.getTime() - new Date(from).getTime())
  const secs = diff / 1000
  const minutes = secs / 60
  const hours = minutes / 60

  if (secs < 60) {
    return `${secs}s`
  }
  else if (minutes < 60) {
    return `${minutes}m`
  }
  else if (hours < 24) {
    return `${hours}h`
  }
  return `${monthNames[from.getMonth()].substring(0, 3)} ${from.getDate()}`
}

export function formatDateString(date: Date): string {
  const d = new Date(date)
  const str = d.toLocaleDateString("en-us", { hour12: true, hour: "2-digit", minute: "2-digit", month: "short", day: "2-digit", year: "numeric" })
  return str.substring(0, 12) + str.substring(13)
}

export function stringToColor(string: string) {
  let hash = 0;
  let i;

  for (i = 0; i < string.length; i += 1) {
    hash = string.charCodeAt(i) + ((hash << 5) - hash);
  }

  let color = '#';

  for (i = 0; i < 3; i += 1) {
    const value = (hash >> (i * 8)) & 0xff;
    color += `00${value.toString(16)}`.slice(-2);
  }

  return color;
}

export function abbreviateNumber(number: number): string {
  if (number > 999999) {
    return `${(Math.floor(number / 1000000))}M`
  }
  if (number > 999) {
    return `${(Math.floor(number / 1000))}K`
  }
  return number.toString()
}