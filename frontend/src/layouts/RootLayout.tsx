import ExploreSideBar from "@/components/ExporeSideBar";
import NavigationSideBar from "@/components/NavigationSideBar";
import { Outlet } from "react-router-dom";

function RootLayout() {

    return (
        <div className="w-full h-full">
            <section className="w-1/3">
                <NavigationSideBar />
            </section>
            <section className="w-1/3">
                <Outlet></Outlet>
            </section>
            <section className="w-1/3">
                <ExploreSideBar />
            </section>
        </div>)
}


export default RootLayout;