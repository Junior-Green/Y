import { ThemeProvider, createTheme } from "@mui/material";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

interface AppProviderProps {
    children: React.ReactNode
}

const queryClient = new QueryClient()

const materialUiTheme = createTheme({

    palette: {
        primary: {
            main: "#1DA1F2"
        },
        secondary: {
            main: "#333639"
        },

        text: {
            primary: "white"
        },
        mode: "dark"
    }
})

function AppProvider({ children }: AppProviderProps) {

    return (
        <QueryClientProvider client={queryClient}>
            <ThemeProvider theme={materialUiTheme}>
                {children}
            </ThemeProvider>
        </QueryClientProvider>
    )

}

export default AppProvider