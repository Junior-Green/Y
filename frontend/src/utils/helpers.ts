
export const isValidEmail = (email: string): boolean => {
    const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return pattern.test(email);
}

export const isValidPhoneNumber = (phoneNumber: string): boolean => {
    const pattern = /^[0-9]{10}$/;
    return pattern.test(phoneNumber);
}

export const isValidateDate = (month: number, day: number, year: number): boolean => {
    const date = new Date(year, month, day)

    return date.getFullYear() === year &&
        date.getMonth() === month &&
        date.getDate() === day;
}


export const refreshPage = () => {
    window.location.reload();
}