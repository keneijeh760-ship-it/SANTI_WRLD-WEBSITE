"use client";

import { createContext, useCallback, useContext, useEffect, useMemo, useReducer } from "react";

export type CartItem = {
  slug: string;
  name: string;
  image: string;
  price: string;
  priceKobo: number;
  quantity: number;
};

type CartState = {
  items: CartItem[];
};

type CartAction =
  | { type: "ADD_ITEM"; payload: Omit<CartItem, "quantity"> }
  | { type: "REMOVE_ITEM"; slug: string }
  | { type: "UPDATE_QUANTITY"; slug: string; quantity: number }
  | { type: "CLEAR" }
  | { type: "HYDRATE"; items: CartItem[] };

function cartReducer(state: CartState, action: CartAction): CartState {
  switch (action.type) {
    case "ADD_ITEM": {
      const existing = state.items.find((i) => i.slug === action.payload.slug);
      if (existing) {
        return {
          items: state.items.map((i) =>
            i.slug === action.payload.slug ? { ...i, quantity: i.quantity + 1 } : i,
          ),
        };
      }
      return { items: [...state.items, { ...action.payload, quantity: 1 }] };
    }
    case "REMOVE_ITEM":
      return { items: state.items.filter((i) => i.slug !== action.slug) };
    case "UPDATE_QUANTITY":
      if (action.quantity <= 0) {
        return { items: state.items.filter((i) => i.slug !== action.slug) };
      }
      return {
        items: state.items.map((i) =>
          i.slug === action.slug ? { ...i, quantity: action.quantity } : i,
        ),
      };
    case "CLEAR":
      return { items: [] };
    case "HYDRATE":
      return { items: action.items };
    default:
      return state;
  }
}

type CartContextValue = {
  items: CartItem[];
  totalItems: number;
  totalKobo: number;
  addItem: (item: Omit<CartItem, "quantity">) => void;
  removeItem: (slug: string) => void;
  updateQuantity: (slug: string, quantity: number) => void;
  clearCart: () => void;
};

const CartContext = createContext<CartContextValue | null>(null);

const STORAGE_KEY = "santi-wrld-cart";

export function CartProvider({ children }: { children: React.ReactNode }) {
  const [state, dispatch] = useReducer(cartReducer, { items: [] });

  useEffect(() => {
    try {
      const saved = localStorage.getItem(STORAGE_KEY);
      if (saved) {
        const items = JSON.parse(saved) as CartItem[];
        if (Array.isArray(items) && items.length > 0) {
          dispatch({ type: "HYDRATE", items });
        }
      }
    } catch {}
  }, []);

  useEffect(() => {
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(state.items));
    } catch {}
  }, [state.items]);

  const addItem = useCallback(
    (item: Omit<CartItem, "quantity">) => dispatch({ type: "ADD_ITEM", payload: item }),
    [],
  );
  const removeItem = useCallback(
    (slug: string) => dispatch({ type: "REMOVE_ITEM", slug }),
    [],
  );
  const updateQuantity = useCallback(
    (slug: string, quantity: number) => dispatch({ type: "UPDATE_QUANTITY", slug, quantity }),
    [],
  );
  const clearCart = useCallback(() => dispatch({ type: "CLEAR" }), []);

  const totalItems = useMemo(
    () => state.items.reduce((sum, i) => sum + i.quantity, 0),
    [state.items],
  );
  const totalKobo = useMemo(
    () => state.items.reduce((sum, i) => sum + i.priceKobo * i.quantity, 0),
    [state.items],
  );

  const value = useMemo<CartContextValue>(
    () => ({ items: state.items, totalItems, totalKobo, addItem, removeItem, updateQuantity, clearCart }),
    [state.items, totalItems, totalKobo, addItem, removeItem, updateQuantity, clearCart],
  );

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}

export function useCart() {
  const ctx = useContext(CartContext);
  if (!ctx) throw new Error("useCart must be used within CartProvider");
  return ctx;
}

export function formatKobo(kobo: number): string {
  return `₦${(kobo / 100).toLocaleString("en-NG")}`;
}
