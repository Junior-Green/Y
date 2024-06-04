/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    screens: {
      sm: '480px',
      md: '768px',
      lg: '976px',
      xl: '1440px',
    },

    extend: {
      colors: {
        y: {
          primary: {
            light: "#FFFFFF",
            dark: "#14171A"
          },
          secondary: {
            light: "#14171A",
            dark: "#FFFFFF"
          },

          gray: {
            100: "#F5F8FA",
            200: "#E1E8ED",
            300: "#AAB8C2",
            400: "#333639",
          },

          accent: {
            blue: "#1DA1F2"
          }
        }
      },
    },
    plugins: [],
  }
}
