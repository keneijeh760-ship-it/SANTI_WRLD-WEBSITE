"use client";

import { siteContent } from "@/lib/data";
import { useCart } from "@/lib/cart-context";
import { ShoppingBag } from "lucide-react";
import Link from "next/link";

const brand = siteContent.brand;

export default function NavBar() {
  const { totalItems } = useCart();

  return (
    <header className="fixed inset-x-0 top-0 z-50 border-b border-white/10 bg-black/70 backdrop-blur">
      <div className="mx-auto flex h-16 max-w-6xl items-center justify-between px-4 md:px-8">
        <Link href="/" className="display-font text-2xl tracking-widest text-white">
          {brand.name}
        </Link>
        <nav className="flex items-center gap-5 text-sm font-medium text-zinc-200">
          <Link href="/" className="hidden transition hover:text-brand-red sm:inline">
            Home
          </Link>
          <Link href="/archives" className="hidden transition hover:text-brand-red sm:inline">
            Archives
          </Link>
          <a href="#waitlist" className="hidden transition hover:text-brand-red sm:inline">
            Waitlist
          </a>
          <Link href="/cart" className="relative flex items-center gap-1 transition hover:text-brand-red">
            <ShoppingBag size={20} />
            {totalItems > 0 && (
              <span className="absolute -right-2 -top-2 flex h-5 w-5 items-center justify-center rounded-full bg-brand-red text-[10px] font-bold text-white">
                {totalItems > 9 ? "9+" : totalItems}
              </span>
            )}
          </Link>
        </nav>
      </div>
    </header>
  );
}
