import ExploreSideBar from "@/components/ExploreSideBar";
import NavigationSideBar from "@/components/NavigationSideBar";
import { Outlet } from "react-router-dom";

function RootLayout() {

    return (
        <div className="w-full h-full flex">
            <section className="w-1/3">
                <NavigationSideBar />
            </section>
            <section className="min-w-[600px] border-x-[1px] border-x-y-gray-400">
                <Outlet></Outlet>
            </section>
            <section className="min-w-[600px]">
                <ExploreSideBar />
            </section>
        </div>)
}


export default RootLayout;