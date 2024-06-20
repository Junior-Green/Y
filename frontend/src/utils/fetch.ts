

export async function GET(endpoint: string): Promise<any> {
    const response = await fetch(`${import.meta.env.VITE_APP_API_URL}${endpoint}`, {
        credentials: "include"
    }
    )
    if (response.status !== 200) {
        throw new Error(`An error occured while fetching endpoint ${endpoint}`)
    }

    return response.json()
}

export async function POST(endpoint: string, body: any): Promise<any> {
    const response = await fetch(`${import.meta.env.VITE_APP_API_URL}${endpoint}`, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body),
        credentials: "include"
    })

    if (response.status !== 200) {
        throw new Error(`An error occured while fetching endpoint ${endpoint}`)
    }

    return response.json()
}



export async function PUT(endpoint: string, body: any): Promise<any> {
    const response = await fetch(`${import.meta.env.VITE_APP_API_URL}${endpoint}`, {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body),
        credentials: "include"
    })

    if (response.status !== 200) {
        throw new Error(`An error occured while fetching endpoint ${endpoint}`)
    }

    return response.json()
}

export async function DELETE(endpoint: string): Promise<any> {
    const response = await fetch(`${import.meta.env.VITE_APP_API_URL}${endpoint}`, {
        method: 'DELETE',
        credentials: "include"
    })

    if (response.status !== 200) {
        throw new Error(`An error occured while fetching endpoint ${endpoint}`)
    }

    return response.json()
}