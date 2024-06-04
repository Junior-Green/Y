
interface LogoProps {
    widthValue: number;
    widthUnit: string;
    hexColor: string
}

function Logo({ hexColor, widthValue, widthUnit }: LogoProps) {

    return (
        <svg width={`${widthValue}${widthUnit}`} version="1.0" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet" viewBox="44.98 44 114.03 116">

            <g transform="translate(0.000000,205.000000) scale(0.100000,-0.100000)" fill={hexColor} stroke="none">
                <path d="M457 1603 c-15 -14 -6 -33 24 -50 48 -28 109 -130 245 -408 l126 -255 -4 -158 c-3 -180 -11 -203 -79 -230 -26 -10 -39 -21 -39 -34 0 -17 14 -18 280 -18 274 0 280 0 280 20 0 14 -12 25 -38 35 -62 25 -67 39 -70 230 l-4 170 150 281 c162 306 188 346 232 364 21 9 30 19 30 36 l0 24 -160 0 -160 0 0 -25 c0 -16 6 -25 15 -25 8 0 24 -9 36 -21 17 -17 20 -28 15 -63 -6 -39 -194 -446 -206 -446 -5 0 -38 70 -152 322 -26 59 -48 121 -48 138 0 34 30 70 60 70 15 0 20 7 20 25 l0 25 -273 0 c-151 0 -277 -3 -280 -7z m480 -382 l143 -303 0 -194 0 -194 -70 0 -70 0 0 194 0 195 -149 306 -150 306 76 -3 76 -3 144 -304z" />
            </g>
        </svg>
    )
}

export default Logo