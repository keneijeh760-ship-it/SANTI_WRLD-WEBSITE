import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
  try {
    const { reference } = await request.json();

    if (!reference || typeof reference !== "string") {
      return NextResponse.json({ verified: false, error: "Missing reference" }, { status: 400 });
    }

    const secretKey = process.env.PAYSTACK_SECRET_KEY;
    if (!secretKey) {
      console.error("PAYSTACK_SECRET_KEY not configured");
      return NextResponse.json({ verified: false, error: "Payment not configured" }, { status: 500 });
    }

    const paystackRes = await fetch(
      `https://api.paystack.co/transaction/verify/${encodeURIComponent(reference)}`,
      {
        headers: {
          Authorization: `Bearer ${secretKey}`,
        },
      },
    );

    const data = await paystackRes.json();

    if (data.status === true && data.data?.status === "success") {
      return NextResponse.json({
        verified: true,
        amount: data.data.amount,
        reference: data.data.reference,
      });
    }

    return NextResponse.json({ verified: false, error: "Payment not successful" });
  } catch (err) {
    console.error("Payment verification error:", err);
    return NextResponse.json({ verified: false, error: "Verification failed" }, { status: 500 });
  }
}
