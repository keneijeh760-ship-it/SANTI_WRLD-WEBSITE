"use client";

import Footer from "@/components/Footer";
import NavBar from "@/components/NavBar";
import { useCart, formatKobo } from "@/lib/cart-context";
import { Minus, Plus, Trash2 } from "lucide-react";
import Image from "next/image";
import Link from "next/link";

function CartItemRow({
  item,
  onUpdate,
  onRemove,
}: {
  item: { slug: string; name: string; image: string; price: string; priceKobo: number; quantity: number };
  onUpdate: (slug: string, qty: number) => void;
  onRemove: (slug: string) => void;
}) {
  return (
    <div className="flex gap-4 rounded-2xl border border-white/10 bg-zinc-950/70 p-3 sm:p-4">
      <div className="relative h-24 w-24 flex-shrink-0 overflow-hidden rounded-xl sm:h-28 sm:w-28">
        <Image src={item.image} alt={item.name} fill sizes="112px" className="object-cover" />
      </div>
      <div className="flex flex-1 flex-col justify-between">
        <div>
          <h3 className="display-font text-lg leading-tight text-white sm:text-xl">{item.name}</h3>
          <p className="mt-1 text-sm font-bold text-brand-gold">{item.price}</p>
        </div>
        <div className="mt-2 flex items-center justify-between">
          <div className="flex items-center gap-1">
            <button
              onClick={() => onUpdate(item.slug, item.quantity - 1)}
              className="flex h-9 w-9 items-center justify-center rounded-full border border-white/20 text-zinc-300 transition hover:border-brand-red hover:text-white active:scale-95"
              aria-label="Decrease quantity"
            >
              <Minus size={14} />
            </button>
            <span className="w-8 text-center text-sm font-semibold">{item.quantity}</span>
            <button
              onClick={() => onUpdate(item.slug, item.quantity + 1)}
              className="flex h-9 w-9 items-center justify-center rounded-full border border-white/20 text-zinc-300 transition hover:border-brand-red hover:text-white active:scale-95"
              aria-label="Increase quantity"
            >
              <Plus size={14} />
            </button>
          </div>
          <button
            onClick={() => onRemove(item.slug)}
            className="flex h-9 w-9 items-center justify-center rounded-full text-zinc-400 transition hover:text-red-400 active:scale-95"
            aria-label="Remove item"
          >
            <Trash2 size={16} />
          </button>
        </div>
      </div>
    </div>
  );
}

function EmptyCart() {
  return (
    <div className="flex flex-col items-center justify-center py-20 text-center">
      <p className="display-font text-4xl text-zinc-500">Your cart is empty</p>
      <p className="mt-3 text-zinc-400">Add some SANTI WRLD pieces to get started.</p>
      <Link
        href="/#available"
        className="mt-6 rounded-full bg-brand-red px-7 py-3 text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-red-500 active:scale-95"
      >
        Browse Pieces
      </Link>
    </div>
  );
}

export default function CartPage() {
  const { items, totalItems, totalKobo, updateQuantity, removeItem } = useCart();

  return (
    <div className="bg-brand-black text-white">
      <NavBar />
      <main className="mx-auto max-w-6xl px-4 pb-32 pt-28 md:px-8 md:pb-16">
        <h1 className="display-font text-5xl text-brand-red md:text-6xl">YOUR CART</h1>

        {items.length === 0 ? (
          <EmptyCart />
        ) : (
          <div className="mt-8 grid gap-8 lg:grid-cols-[1fr_340px]">
            {/* Items list */}
            <div className="flex flex-col gap-4">
              {items.map((item) => (
                <CartItemRow
                  key={item.slug}
                  item={item}
                  onUpdate={updateQuantity}
                  onRemove={removeItem}
                />
              ))}
              <Link
                href="/#available"
                className="mt-2 text-sm text-zinc-400 transition hover:text-brand-gold"
              >
                &larr; Continue shopping
              </Link>
            </div>

            {/* Order summary -- sidebar on desktop */}
            <div className="hidden rounded-2xl border border-white/10 bg-zinc-950/70 p-6 lg:block">
              <h2 className="display-font text-2xl">Order Summary</h2>
              <div className="mt-4 space-y-3 border-b border-white/10 pb-4">
                {items.map((item) => (
                  <div key={item.slug} className="flex justify-between text-sm text-zinc-300">
                    <span>
                      {item.name} &times; {item.quantity}
                    </span>
                    <span>{formatKobo(item.priceKobo * item.quantity)}</span>
                  </div>
                ))}
              </div>
              <div className="mt-4 flex justify-between text-lg font-bold">
                <span>Total</span>
                <span className="text-brand-gold">{formatKobo(totalKobo)}</span>
              </div>
              <Link
                href="/checkout"
                className="mt-6 flex h-12 w-full items-center justify-center rounded-full bg-brand-red text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-red-500 active:scale-95"
              >
                Checkout ({totalItems} {totalItems === 1 ? "item" : "items"})
              </Link>
            </div>
          </div>
        )}
      </main>

      {/* Sticky mobile checkout bar */}
      {items.length > 0 && (
        <div className="fixed inset-x-0 bottom-0 z-40 border-t border-white/10 bg-black/90 px-4 py-3 backdrop-blur lg:hidden">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-xs text-zinc-400">{totalItems} {totalItems === 1 ? "item" : "items"}</p>
              <p className="text-lg font-bold text-brand-gold">{formatKobo(totalKobo)}</p>
            </div>
            <Link
              href="/checkout"
              className="flex h-12 items-center justify-center rounded-full bg-brand-red px-8 text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-red-500 active:scale-95"
            >
              Checkout
            </Link>
          </div>
        </div>
      )}

      <Footer />
    </div>
  );
}
