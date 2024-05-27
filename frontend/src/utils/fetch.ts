import * as process from 'node:process'

export async function GET(endpoint: string): Promise<any> {
    const response = await fetch(`${process.env.REACT_APP_API_URL}${endpoint}`)

    if (response.status !== 200) {
        throw new Error(`An error occured while fetching endpoint ${endpoint}`)
    }

    return response.json()
}

export async function POST(endpoint: string, body: any): Promise<any> {
    const response = await fetch(`${process.env.REACT_APP_API_URL}${endpoint}`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })

    if (response.status !== 200) {
        throw new Error(`An error occured while fetching endpoint ${endpoint}`)
    }

    return response.json()
}



export async function PUT(endpoint: string, body: any): Promise<any> {
    const response = await fetch(`${process.env.REACT_APP_API_URL}${endpoint}`, {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    })

    if (response.status !== 200) {
        throw new Error(`An error occured while fetching endpoint ${endpoint}`)
    }

    return response.json()
}

export async function DELETE(endpoint: string): Promise<any> {
    const response = await fetch(`${process.env.REACT_APP_API_URL}${endpoint}`, {
        method: 'PUT',
    })

    if (response.status !== 200) {
        throw new Error(`An error occured while fetching endpoint ${endpoint}`)
    }

    return response.json()
}