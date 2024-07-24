import axios from "axios"

export async function GET(endpoint: string): Promise<any> {
    return axios({
        url: import.meta.env.DEV ? `${import.meta.env.VITE_APP_API_URL}${endpoint}` : endpoint,
        method: "get",
        withCredentials: import.meta.env.DEV,
        responseType: 'json',
    })
}

export async function POST(endpoint: string, body: any): Promise<any> {
    return axios({
        url: import.meta.env.DEV ? `${import.meta.env.VITE_APP_API_URL}${endpoint}` : endpoint,
        method: 'post',
        withCredentials: import.meta.env.DEV,
        responseType: 'json',
        data: body,
        headers: {
            "Content-Type": "application/json"
        }
    })
}

export async function PUT(endpoint: string, body: any): Promise<any> {
    return axios({
        url: import.meta.env.DEV ? `${import.meta.env.VITE_APP_API_URL}${endpoint}` : endpoint,
        method: "put",
        withCredentials: import.meta.env.DEV,
        responseType: 'json',
        data: body,
        headers: {
            "Content-Type": "application/json"
        }
    })
}

export async function DELETE(endpoint: string): Promise<any> {
    return axios({
        url: import.meta.env.DEV ? `${import.meta.env.VITE_APP_API_URL}${endpoint}` : endpoint,
        method: "delete",
        withCredentials: import.meta.env.DEV,
        responseType: 'json',
        headers: {
            "Content-Type": "application/json"
        }
    })
}