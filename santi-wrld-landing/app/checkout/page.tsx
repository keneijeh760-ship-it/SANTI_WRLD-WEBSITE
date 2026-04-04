"use client";

import Footer from "@/components/Footer";
import NavBar from "@/components/NavBar";
import { formatKobo, useCart } from "@/lib/cart-context";
import { CheckCircle2 } from "lucide-react";
import Link from "next/link";
import { FormEvent, useCallback, useRef, useState } from "react";

declare global {
  interface Window {
    PaystackPop: {
      setup: (opts: {
        key: string;
        email: string;
        amount: number;
        currency: string;
        ref: string;
        metadata: Record<string, unknown>;
        onClose: () => void;
        callback: (response: { reference: string }) => void;
      }) => { openIframe: () => void };
    };
  }
}

function generateRef() {
  return `SW-${Date.now()}-${Math.random().toString(36).slice(2, 8)}`;
}

type FormData = {
  fullName: string;
  email: string;
  phone: string;
  address: string;
  city: string;
  state: string;
};

export default function CheckoutPage() {
  const { items, totalKobo, totalItems, clearCart } = useCart();
  const [form, setForm] = useState<FormData>({
    fullName: "",
    email: "",
    phone: "",
    address: "",
    city: "",
    state: "",
  });
  const [status, setStatus] = useState<"idle" | "processing" | "success" | "error">("idle");
  const [errorMsg, setErrorMsg] = useState("");
  const payRef = useRef("");

  const update = useCallback(
    (field: keyof FormData) => (e: React.ChangeEvent<HTMLInputElement>) =>
      setForm((prev) => ({ ...prev, [field]: e.target.value })),
    [],
  );

  async function verifyPayment(reference: string): Promise<boolean> {
    try {
      const res = await fetch("/api/verify-payment", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ reference }),
      });
      const data = await res.json();
      return data.verified === true;
    } catch {
      return false;
    }
  }

  function handleSubmit(e: FormEvent) {
    e.preventDefault();
    if (items.length === 0) return;

    const paystackKey = process.env.NEXT_PUBLIC_PAYSTACK_PUBLIC_KEY;
    if (!paystackKey) {
      setStatus("error");
      setErrorMsg("Payment is not configured. Please contact us on Instagram @sntdept.");
      return;
    }

    if (typeof window.PaystackPop === "undefined") {
      setStatus("error");
      setErrorMsg("Payment service is loading. Please try again in a moment.");
      return;
    }

    setStatus("processing");
    setErrorMsg("");
    payRef.current = generateRef();

    const handler = window.PaystackPop.setup({
      key: paystackKey,
      email: form.email,
      amount: totalKobo,
      currency: "NGN",
      ref: payRef.current,
      metadata: {
        custom_fields: [
          { display_name: "Full Name", variable_name: "full_name", value: form.fullName },
          { display_name: "Phone", variable_name: "phone", value: form.phone },
          { display_name: "Address", variable_name: "address", value: `${form.address}, ${form.city}, ${form.state}` },
          { display_name: "Items", variable_name: "items", value: items.map((i) => `${i.name} x${i.quantity}`).join(", ") },
        ],
      },
      onClose: () => {
        if (status !== "success") {
          setStatus("idle");
        }
      },
      callback: async (response) => {
        const verified = await verifyPayment(response.reference);
        if (verified) {
          setStatus("success");
          clearCart();
        } else {
          setStatus("error");
          setErrorMsg("Payment verification failed. Contact us with ref: " + response.reference);
        }
      },
    });

    handler.openIframe();
  }

  if (status === "success") {
    return (
      <div className="bg-brand-black text-white">
        <NavBar />
        <main className="mx-auto flex min-h-[80vh] max-w-xl flex-col items-center justify-center px-4 pt-28 text-center">
          <CheckCircle2 size={64} className="text-green-400" />
          <h1 className="display-font mt-4 text-5xl text-brand-red md:text-6xl">ORDER CONFIRMED</h1>
          <p className="mt-4 text-zinc-300">
            Your payment was successful. We&apos;ll reach out with delivery details soon.
          </p>
          <p className="mt-2 text-sm text-zinc-500">Reference: {payRef.current}</p>
          <Link
            href="/"
            className="mt-8 rounded-full bg-brand-red px-7 py-3 text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-red-500 active:scale-95"
          >
            Back to Home
          </Link>
        </main>
        <Footer />
      </div>
    );
  }

  if (items.length === 0 && status !== "success") {
    return (
      <div className="bg-brand-black text-white">
        <NavBar />
        <main className="mx-auto flex min-h-[80vh] max-w-xl flex-col items-center justify-center px-4 pt-28 text-center">
          <p className="display-font text-4xl text-zinc-500">Nothing to checkout</p>
          <Link
            href="/#available"
            className="mt-6 rounded-full bg-brand-red px-7 py-3 text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-red-500 active:scale-95"
          >
            Browse Pieces
          </Link>
        </main>
        <Footer />
      </div>
    );
  }

  return (
    <div className="bg-brand-black text-white">
      <NavBar />
      <main className="mx-auto max-w-6xl px-4 pb-32 pt-28 md:px-8 md:pb-16">
        <h1 className="display-font text-5xl text-brand-red md:text-6xl">CHECKOUT</h1>

        <div className="mt-8 grid gap-8 lg:grid-cols-[1fr_380px]">
          {/* Form */}
          <form id="checkout-form" onSubmit={handleSubmit} className="space-y-5">
            <h2 className="display-font text-2xl">Delivery Details</h2>

            <div>
              <label htmlFor="fullName" className="mb-1 block text-sm text-zinc-400">Full Name</label>
              <input
                id="fullName"
                type="text"
                required
                value={form.fullName}
                onChange={update("fullName")}
                className="h-12 w-full rounded-xl border border-white/20 bg-black/60 px-4 text-white outline-none transition focus:border-brand-red"
              />
            </div>

            <div className="grid gap-5 sm:grid-cols-2">
              <div>
                <label htmlFor="email" className="mb-1 block text-sm text-zinc-400">Email</label>
                <input
                  id="email"
                  type="email"
                  inputMode="email"
                  required
                  value={form.email}
                  onChange={update("email")}
                  className="h-12 w-full rounded-xl border border-white/20 bg-black/60 px-4 text-white outline-none transition focus:border-brand-red"
                />
              </div>
              <div>
                <label htmlFor="phone" className="mb-1 block text-sm text-zinc-400">Phone</label>
                <input
                  id="phone"
                  type="tel"
                  inputMode="tel"
                  required
                  value={form.phone}
                  onChange={update("phone")}
                  className="h-12 w-full rounded-xl border border-white/20 bg-black/60 px-4 text-white outline-none transition focus:border-brand-red"
                />
              </div>
            </div>

            <div>
              <label htmlFor="address" className="mb-1 block text-sm text-zinc-400">Delivery Address</label>
              <input
                id="address"
                type="text"
                required
                value={form.address}
                onChange={update("address")}
                className="h-12 w-full rounded-xl border border-white/20 bg-black/60 px-4 text-white outline-none transition focus:border-brand-red"
              />
            </div>

            <div className="grid gap-5 sm:grid-cols-2">
              <div>
                <label htmlFor="city" className="mb-1 block text-sm text-zinc-400">City</label>
                <input
                  id="city"
                  type="text"
                  required
                  value={form.city}
                  onChange={update("city")}
                  className="h-12 w-full rounded-xl border border-white/20 bg-black/60 px-4 text-white outline-none transition focus:border-brand-red"
                />
              </div>
              <div>
                <label htmlFor="state" className="mb-1 block text-sm text-zinc-400">State</label>
                <input
                  id="state"
                  type="text"
                  required
                  value={form.state}
                  onChange={update("state")}
                  className="h-12 w-full rounded-xl border border-white/20 bg-black/60 px-4 text-white outline-none transition focus:border-brand-red"
                />
              </div>
            </div>

            {errorMsg && <p className="text-sm text-red-400">{errorMsg}</p>}

            {/* Desktop pay button */}
            <button
              type="submit"
              disabled={status === "processing"}
              className="hidden h-12 w-full items-center justify-center rounded-full bg-brand-red text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-red-500 disabled:opacity-60 active:scale-95 lg:flex"
            >
              {status === "processing" ? "Processing..." : `Pay ${formatKobo(totalKobo)}`}
            </button>
          </form>

          {/* Order summary sidebar */}
          <div className="rounded-2xl border border-white/10 bg-zinc-950/70 p-6">
            <h2 className="display-font text-2xl">Order Summary</h2>
            <div className="mt-4 space-y-3 border-b border-white/10 pb-4">
              {items.map((item) => (
                <div key={item.slug} className="flex justify-between text-sm text-zinc-300">
                  <span>{item.name} &times; {item.quantity}</span>
                  <span>{formatKobo(item.priceKobo * item.quantity)}</span>
                </div>
              ))}
            </div>
            <div className="mt-4 flex justify-between text-lg font-bold">
              <span>Total</span>
              <span className="text-brand-gold">{formatKobo(totalKobo)}</span>
            </div>
            <p className="mt-3 text-xs text-zinc-500">{totalItems} {totalItems === 1 ? "item" : "items"}</p>
          </div>
        </div>
      </main>

      {/* Sticky mobile pay bar */}
      <div className="fixed inset-x-0 bottom-0 z-40 border-t border-white/10 bg-black/90 px-4 py-3 backdrop-blur lg:hidden">
        <div className="flex items-center justify-between">
          <div>
            <p className="text-xs text-zinc-400">{totalItems} {totalItems === 1 ? "item" : "items"}</p>
            <p className="text-lg font-bold text-brand-gold">{formatKobo(totalKobo)}</p>
          </div>
          <button
            type="submit"
            form="checkout-form"
            disabled={status === "processing"}
            className="flex h-12 items-center justify-center rounded-full bg-brand-red px-8 text-sm font-semibold uppercase tracking-wider text-white transition hover:bg-red-500 disabled:opacity-60 active:scale-95"
          >
            {status === "processing" ? "Processing..." : "Pay Now"}
          </button>
        </div>
      </div>

      <Footer />
    </div>
  );
}
