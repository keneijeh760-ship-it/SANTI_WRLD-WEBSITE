import { getSupabaseAdmin } from "@/lib/supabase";
import { NextRequest, NextResponse } from "next/server";
import { Resend } from "resend";

const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

function getResend() {
  const key = process.env.RESEND_API_KEY;
  if (!key) return null;
  return new Resend(key);
}

async function sendConfirmationEmail(email: string) {
  const resend = getResend();
  if (!resend) return;

  const fromAddress = process.env.RESEND_FROM_EMAIL ?? "SANTI WRLD <onboarding@resend.dev>";

  await resend.emails.send({
    from: fromAddress,
    to: email,
    subject: "You're on the SANTI WRLD Waitlist",
    html: `
      <div style="background-color:#0a0a0a;color:#f5f5f5;font-family:Arial,Helvetica,sans-serif;padding:40px 20px;text-align:center;">
        <h1 style="font-size:48px;letter-spacing:0.05em;margin:0;color:#e31b23;">SANTI WRLD</h1>
        <p style="color:#c8a951;font-size:14px;letter-spacing:0.2em;margin-top:8px;">LOVE NEVER DIES</p>
        <hr style="border:none;border-top:1px solid rgba(255,255,255,0.1);margin:32px auto;max-width:400px;" />
        <h2 style="font-size:24px;margin:0 0 12px;">You're In.</h2>
        <p style="color:#d4d4d8;font-size:16px;line-height:1.6;max-width:480px;margin:0 auto;">
          Welcome to the waitlist. You'll be the first to know when our next drop goes live &mdash;
          limited pieces, no restocks.
        </p>
        <p style="color:#d4d4d8;font-size:16px;line-height:1.6;max-width:480px;margin:16px auto 0;">
          Stay locked in.
        </p>
        <div style="margin-top:32px;">
          <a href="https://instagram.com/sntdept" style="color:#c8a951;text-decoration:none;font-size:14px;">@sntdept</a>
        </div>
        <p style="color:#71717a;font-size:12px;margin-top:40px;">&copy; 2026 SANTI WRLD</p>
      </div>
    `,
  });
}

export async function POST(request: NextRequest) {
  try {
    const body = await request.json();
    const email = String(body?.email ?? "").trim().toLowerCase();

    if (!EMAIL_REGEX.test(email)) {
      return NextResponse.json({ error: "Please enter a valid email address." }, { status: 400 });
    }

    let saved = false;

    try {
      const supabase = getSupabaseAdmin();
      const { error } = await supabase.from("waitlist").insert({ email });

      if (error?.message?.toLowerCase().includes("duplicate")) {
        return NextResponse.json({ error: "This email is already on the waitlist." }, { status: 409 });
      }

      if (error) {
        console.error("Supabase insert error:", error.message);
      } else {
        saved = true;
      }
    } catch (e) {
      console.error("Supabase unavailable:", e);
    }

    try {
      await sendConfirmationEmail(email);
    } catch (e) {
      console.error("Failed to send confirmation email:", e);
    }

    if (!saved) {
      return NextResponse.json(
        { ok: true, warning: "Email sent but database save failed. Check Supabase config." },
        { status: 200 },
      );
    }

    return NextResponse.json({ ok: true }, { status: 200 });
  } catch {
    return NextResponse.json(
      { error: "Server error. Check your .env.local configuration." },
      { status: 500 },
    );
  }
}
