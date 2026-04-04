import Providers from "@/components/Providers";
import type { Metadata } from "next";
import { Bebas_Neue, Inter } from "next/font/google";
import Script from "next/script";
import "./globals.css";

const display = Bebas_Neue({
  variable: "--font-display",
  subsets: ["latin"],
  weight: "400",
});

const inter = Inter({
  variable: "--font-body",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "SANTI WRLD",
  description:
    "Official SANTI WRLD page. Explore available pieces, lookbook archives, and join the waitlist for upcoming drops.",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className={`${display.variable} ${inter.variable} h-full`}>
      <body className="min-h-full bg-brand-black text-white antialiased">
        <Providers>{children}</Providers>
        <Script src="https://js.paystack.co/v2/inline.js" strategy="lazyOnload" />
      </body>
    </html>
  );
}
