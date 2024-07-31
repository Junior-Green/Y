import ExploreSideBar from "@/components/ExploreSideBar";
import NavigationSideBar from "@/components/NavigationSideBar";
import { Outlet } from "react-router-dom";

function RootLayout() {

    return (
        <div className="w-full h-full flex">
            <section className="w-auto md:w-1/3">
                <NavigationSideBar />
            </section>
            <section className="w-full md:max-w-[600px] md:min-w-[600px] border-x-[1px] border-x-y-gray-400 overflow-y-scroll" >
                <Outlet></Outlet>
            </section>
            <section className="w-0 md:min-w-[600px] ">
                <ExploreSideBar />
            </section>
        </div>)
}


export default RootLayout;