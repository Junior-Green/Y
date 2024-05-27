import { store } from "@/stores/store";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Provider } from "react-redux";

interface AppProviderProps {
    children: React.ReactNode
}

const queryClient = new QueryClient()



function AppProvider({ children }: AppProviderProps) {

    return (
        <QueryClientProvider client={queryClient}>
            <Provider store={store}>
                {children}
            </Provider>
        </QueryClientProvider>
    )

}

export default AppProvider