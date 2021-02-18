package com.example.ledclone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;

import androidx.core.view.MotionEventCompat;
import androidx.customview.widget.ViewDragHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ShowView extends SurfaceView implements SurfaceHolder.Callback {
    public static int left = 0;
    public SurfaceHolder holder = getHolder();

    public ShowView(Context context) {
        super(context);
        this.holder.addCallback(this);
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        new DrawThread().start();
    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
    }

    public void drawBg(Canvas canvas) {
        if (canvas != null) {
            int width = canvas.getWidth();
            int height = MainActivity.pix;
            Paint paint = new Paint();
            paint.setColor(-16777216);
            for (int h = 0; h <= canvas.getHeight(); h++) {
                for (int w = 0; w <= width; w++) {
                    canvas.drawPoint((float) w, (float) h, paint);
                }
            }
            for (int h2 = 1; h2 <= height; h2++) {
                for (int w2 = 1; w2 <= width; w2++) {
//                    canvas.drawBitmap(MainActivity.ledH, (float) (((w2 - 1) * 4) + w2), (float) (((h2 - 1) * 4) + h2), paint);
                }
            }
            Paint paint2 = new Paint();
            paint2.setColor(Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK));
            for (int h3 = height * 5; h3 <= canvas.getHeight(); h3++) {
                for (int w3 = 0; w3 <= width; w3++) {
                    canvas.drawPoint((float) w3, (float) h3, paint2);
                }
            }
        }
    }

    public void drawImage(Canvas canvas, String pathName) {
        if (pathName.length() != 0) {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(pathName);
                int width = bitmap.getWidth();
                int height = MainActivity.pix;
                Paint p = new Paint();
                p.setColor(-65536);
                for (int h = 1; h <= height; h++) {
                    for (int w = 1; w <= width; w++) {
                        if (getColorLD(bitmap.getPixel(w - 1, h - 1)) > 127.0f) {
                            canvas.drawBitmap(MainActivity.ledL, (float) (left + w + ((w - 1) * 4)), (float) (((h - 1) * 4) + h), p);
                        }
                    }
                }
                bitmap.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void drawText(Canvas canvas, String text, String font, int fontSize, int fontStyle) {
        if (text.length() != 0) {
            Typeface f = Typeface.DEFAULT;
            if (font != null && font.length() > 0) {
                f = Typeface.createFromFile(font);
            }
            Paint paint = new Paint();
            paint.setColor(-65536);
            paint.setTextSize((float) fontSize);
            paint.setTypeface(f);
            Paint.FontMetrics fm = paint.getFontMetrics();
            if (fontStyle == 1) {
                paint.setFakeBoldText(true);
            } else if (fontStyle == 2) {
                paint.setTextSkewX(-0.5f);
            } else if (fontStyle == 3) {
                paint.setFakeBoldText(true);
                paint.setTextSkewX(-0.5f);
            }
            Bitmap b = Bitmap.createBitmap(getFontWidth(paint, text), Math.round(fm.bottom - fm.ascent), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            c.drawARGB(0, 0, 0, 0);
            c.drawText(text, 0.0f, getFontTop(paint) - 1.0f, paint);
            int width = b.getWidth();
            int height = MainActivity.pix > b.getHeight() ? b.getHeight() : MainActivity.pix;
            Paint p = new Paint();
            p.setColor(-65536);
            for (int h = 1; h <= height; h++) {
                for (int w = 1; w <= width; w++) {
                    if (b.getPixel(w - 1, h - 1) != 0) {
//                        canvas.drawBitmap(MainActivity.ledL, (float) (left + w + ((w - 1) * 4)), (float) (((h - 1) * 4) + h), p);
                    }
                }
            }
            b.recycle();
        }
    }

    public static void drawQianru(Canvas canvas, Map<String, String> map) {
        int qianru = Integer.parseInt(map.get("qianru"));
        int fontSize = MainActivity.pix;
        switch (qianru) {
            case 1:
                String text = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Typeface f = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint = new Paint();
                paint.setColor(-65536);
                paint.setTextSize((float) fontSize);
                paint.setTypeface(f);
                Paint.FontMetrics fm = paint.getFontMetrics();
                if (0 == 1) {
                    paint.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint.setFakeBoldText(true);
                    paint.setTextSkewX(-0.5f);
                }
                Bitmap b = Bitmap.createBitmap(getFontWidth(paint, text), Math.round(fm.bottom - fm.ascent), Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                c.drawARGB(0, 0, 0, 0);
                c.drawText(text, 0.0f, getFontTop(paint) - 1.0f, paint);
                Paint p = new Paint();
                p.setColor(-65536);
                new Paint().setColor(-16777216);
                int width = b.getWidth();
                left = (width * 5) - 1;
                int height = MainActivity.pix > b.getHeight() ? b.getHeight() : MainActivity.pix;
                for (int h = 1; h <= height; h++) {
                    for (int w = 1; w <= width; w++) {
                        if (b.getPixel(w - 1, h - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) (((w - 1) * 4) + w), (float) (((h - 1) * 4) + h), p);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) (((w - 1) * 4) + w), (float) (((h - 1) * 4) + h), p);
                        }
                    }
                }
                b.recycle();
                return;
            case 2:
                String text2 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Typeface f2 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f2 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint2 = new Paint();
                paint2.setColor(-65536);
                paint2.setTextSize((float) fontSize);
                paint2.setTypeface(f2);
                Paint.FontMetrics fm2 = paint2.getFontMetrics();
                if (0 == 1) {
                    paint2.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint2.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint2.setFakeBoldText(true);
                    paint2.setTextSkewX(-0.5f);
                }
                Bitmap b2 = Bitmap.createBitmap(getFontWidth(paint2, text2), Math.round(fm2.bottom - fm2.ascent), Bitmap.Config.ARGB_8888);
                Canvas c2 = new Canvas(b2);
                c2.drawARGB(0, 0, 0, 0);
                c2.drawText(text2, 0.0f, getFontTop(paint2) - 1.0f, paint2);
                Paint p2 = new Paint();
                p2.setColor(-65536);
                Paint p22 = new Paint();
                p22.setColor(-16777216);
                int width2 = b2.getWidth();
                int height2 = MainActivity.pix > b2.getHeight() ? b2.getHeight() : MainActivity.pix;
                for (int h2 = 1; h2 <= height2; h2++) {
                    for (int w2 = 1; w2 <= width2; w2++) {
                        if (b2.getPixel(w2 - 1, h2 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) ((((canvas.getWidth() - (width2 * 4)) - 4) - w2) + w2 + ((w2 - 1) * 4)), (float) (((h2 - 1) * 4) + h2), p2);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) ((((canvas.getWidth() - (width2 * 4)) - 4) - w2) + w2 + ((w2 - 1) * 4)), (float) (((h2 - 1) * 4) + h2), p22);
                        }
                    }
                }
                b2.recycle();
                return;
            case 3:
                String text3 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Typeface f3 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f3 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint3 = new Paint();
                paint3.setColor(-65536);
                paint3.setTextSize((float) fontSize);
                paint3.setTypeface(f3);
                Paint.FontMetrics fm3 = paint3.getFontMetrics();
                if (0 == 1) {
                    paint3.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint3.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint3.setFakeBoldText(true);
                    paint3.setTextSkewX(-0.5f);
                }
                Bitmap b3 = Bitmap.createBitmap(getFontWidth(paint3, text3), Math.round(fm3.bottom - fm3.ascent), Bitmap.Config.ARGB_8888);
                Canvas c3 = new Canvas(b3);
                c3.drawARGB(0, 0, 0, 0);
                c3.drawText(text3, 0.0f, getFontTop(paint3) - 1.0f, paint3);
                Paint p3 = new Paint();
                p3.setColor(-65536);
                Paint p23 = new Paint();
                p23.setColor(-16777216);
                int width3 = b3.getWidth();
                int height3 = MainActivity.pix > b3.getHeight() ? b3.getHeight() : MainActivity.pix;
                RectF r = new RectF();
                r.left = (float) (((canvas.getWidth() / 2) - (width3 * 2)) - 1);
                r.top = 0.0f;
                r.bottom = (float) (MainActivity.pix * 5);
                r.right = (float) (((canvas.getWidth() / 2) + (width3 * 3)) - 1);
                paint3.setColor(-16777216);
                canvas.drawRect(r, paint3);
                for (int h3 = 1; h3 <= height3; h3++) {
                    for (int w3 = 1; w3 <= width3; w3++) {
                        if (b3.getPixel(w3 - 1, h3 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) (((canvas.getWidth() / 2) - (width3 * 2)) + w3 + ((w3 - 1) * 4)), (float) (((h3 - 1) * 4) + h3), p3);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) (((canvas.getWidth() / 2) - (width3 * 2)) + w3 + ((w3 - 1) * 4)), (float) (((h3 - 1) * 4) + h3), p23);
                        }
                    }
                }
                b3.recycle();
                return;
            case 4:
                String text4 = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Typeface f4 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty)!= 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f4 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint4 = new Paint();
                paint4.setColor(-65536);
                paint4.setTextSize((float) fontSize);
                paint4.setTypeface(f4);
                Paint.FontMetrics fm4 = paint4.getFontMetrics();
                if (0 == 1) {
                    paint4.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint4.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint4.setFakeBoldText(true);
                    paint4.setTextSkewX(-0.5f);
                }
                Bitmap b4 = Bitmap.createBitmap(getFontWidth(paint4, text4), Math.round(fm4.bottom - fm4.ascent), Bitmap.Config.ARGB_8888);
                Canvas c4 = new Canvas(b4);
                c4.drawARGB(0, 0, 0, 0);
                c4.drawText(text4, 0.0f, getFontTop(paint4) - 1.0f, paint4);
                Paint p4 = new Paint();
                p4.setColor(-65536);
                new Paint().setColor(-16777216);
                int width4 = b4.getWidth();
                left = (width4 * 5) - 1;
                int height4 = MainActivity.pix > b4.getHeight() ? b4.getHeight() : MainActivity.pix;
                for (int h4 = 1; h4 <= height4; h4++) {
                    for (int w4 = 1; w4 <= width4; w4++) {
                        if (b4.getPixel(w4 - 1, h4 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) (((w4 - 1) * 4) + w4), (float) (((h4 - 1) * 4) + h4), p4);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) (((w4 - 1) * 4) + w4), (float) (((h4 - 1) * 4) + h4), p4);
                        }
                    }
                }
                b4.recycle();
                return;
            case 5:
                String text5 = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Typeface f5 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f5 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint5 = new Paint();
                paint5.setColor(-65536);
                paint5.setTextSize((float) fontSize);
                paint5.setTypeface(f5);
                Paint.FontMetrics fm5 = paint5.getFontMetrics();
                if (0 == 1) {
                    paint5.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint5.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint5.setFakeBoldText(true);
                    paint5.setTextSkewX(-0.5f);
                }
                Bitmap b5 = Bitmap.createBitmap(getFontWidth(paint5, text5), Math.round(fm5.bottom - fm5.ascent), Bitmap.Config.ARGB_8888);
                Canvas c5 = new Canvas(b5);
                c5.drawARGB(0, 0, 0, 0);
                c5.drawText(text5, 0.0f, getFontTop(paint5) - 1.0f, paint5);
                Paint p5 = new Paint();
                p5.setColor(-65536);
                Paint p24 = new Paint();
                p24.setColor(-16777216);
                int width5 = b5.getWidth();
                int height5 = MainActivity.pix > b5.getHeight() ? b5.getHeight() : MainActivity.pix;
                for (int h5 = 1; h5 <= height5; h5++) {
                    for (int w5 = 1; w5 <= width5; w5++) {
                        if (b5.getPixel(w5 - 1, h5 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) ((((canvas.getWidth() - (width5 * 4)) - 4) - w5) + w5 + ((w5 - 1) * 4)), (float) (((h5 - 1) * 4) + h5), p5);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) ((((canvas.getWidth() - (width5 * 4)) - 4) - w5) + w5 + ((w5 - 1) * 4)), (float) (((h5 - 1) * 4) + h5), p24);
                        }
                    }
                }
                b5.recycle();
                return;
            case 6:
                String text6 = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                Typeface f6 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty)!= 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f6 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint6 = new Paint();
                paint6.setColor(-65536);
                paint6.setTextSize((float) fontSize);
                paint6.setTypeface(f6);
                Paint.FontMetrics fm6 = paint6.getFontMetrics();
                if (0 == 1) {
                    paint6.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint6.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint6.setFakeBoldText(true);
                    paint6.setTextSkewX(-0.5f);
                }
                Bitmap b6 = Bitmap.createBitmap(getFontWidth(paint6, text6), Math.round(fm6.bottom - fm6.ascent), Bitmap.Config.ARGB_8888);
                Canvas c6 = new Canvas(b6);
                c6.drawARGB(0, 0, 0, 0);
                c6.drawText(text6, 0.0f, getFontTop(paint6) - 1.0f, paint6);
                Paint p6 = new Paint();
                p6.setColor(-65536);
                Paint p25 = new Paint();
                p25.setColor(-16777216);
                int width6 = b6.getWidth();
                int height6 = MainActivity.pix > b6.getHeight() ? b6.getHeight() : MainActivity.pix;
                RectF r2 = new RectF();
                r2.left = (float) ((canvas.getWidth() / 2) - (width6 * 2));
                r2.top = 0.0f;
                r2.bottom = (float) (MainActivity.pix * 5);
                r2.right = (float) (((canvas.getWidth() / 2) + (width6 * 3)) - 1);
                paint6.setColor(-16777216);
                canvas.drawRect(r2, paint6);
                for (int h6 = 1; h6 <= height6; h6++) {
                    for (int w6 = 1; w6 <= width6; w6++) {
                        if (b6.getPixel(w6 - 1, h6 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) (((canvas.getWidth() / 2) - (width6 * 2)) + w6 + ((w6 - 1) * 4)), (float) (((h6 - 1) * 4) + h6), p6);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) (((canvas.getWidth() / 2) - (width6 * 2)) + w6 + ((w6 - 1) * 4)), (float) (((h6 - 1) * 4) + h6), p25);
                        }
                    }
                }
                b6.recycle();
                return;
            case MotionEventCompat.ACTION_HOVER_MOVE:
                String text7 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                Typeface f7 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f7 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint7 = new Paint();
                paint7.setColor(-65536);
                paint7.setTextSize((float) fontSize);
                paint7.setTypeface(f7);
                Paint.FontMetrics fm7 = paint7.getFontMetrics();
                if (0 == 1) {
                    paint7.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint7.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint7.setFakeBoldText(true);
                    paint7.setTextSkewX(-0.5f);
                }
                Bitmap b7 = Bitmap.createBitmap(getFontWidth(paint7, text7), Math.round(fm7.bottom - fm7.ascent), Bitmap.Config.ARGB_8888);
                Canvas c7 = new Canvas(b7);
                c7.drawARGB(0, 0, 0, 0);
                c7.drawText(text7, 0.0f, getFontTop(paint7) - 1.0f, paint7);
                Paint p7 = new Paint();
                p7.setColor(-65536);
                new Paint().setColor(-16777216);
                int width7 = b7.getWidth();
                left = (width7 * 5) - 1;
                int height7 = MainActivity.pix > b7.getHeight() ? b7.getHeight() : MainActivity.pix;
                for (int h7 = 1; h7 <= height7; h7++) {
                    for (int w7 = 1; w7 <= width7; w7++) {
                        if (b7.getPixel(w7 - 1, h7 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) (((w7 - 1) * 4) + w7), (float) (((h7 - 1) * 4) + h7), p7);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) (((w7 - 1) * 4) + w7), (float) (((h7 - 1) * 4) + h7), p7);
                        }
                    }
                }
                b7.recycle();
                return;
            case 8:
                String text8 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                Typeface f8 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f8 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint8 = new Paint();
                paint8.setColor(-65536);
                paint8.setTextSize((float) fontSize);
                paint8.setTypeface(f8);
                Paint.FontMetrics fm8 = paint8.getFontMetrics();
                if (0 == 1) {
                    paint8.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint8.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint8.setFakeBoldText(true);
                    paint8.setTextSkewX(-0.5f);
                }
                Bitmap b8 = Bitmap.createBitmap(getFontWidth(paint8, text8), Math.round(fm8.bottom - fm8.ascent), Bitmap.Config.ARGB_8888);
                Canvas c8 = new Canvas(b8);
                c8.drawARGB(0, 0, 0, 0);
                c8.drawText(text8, 0.0f, getFontTop(paint8) - 1.0f, paint8);
                Paint p8 = new Paint();
                p8.setColor(-65536);
                Paint p26 = new Paint();
                p26.setColor(-16777216);
                int width8 = b8.getWidth();
                int height8 = MainActivity.pix > b8.getHeight() ? b8.getHeight() : MainActivity.pix;
                for (int h8 = 1; h8 <= height8; h8++) {
                    for (int w8 = 1; w8 <= width8; w8++) {
                        if (b8.getPixel(w8 - 1, h8 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) ((((canvas.getWidth() - (width8 * 4)) - 4) - w8) + w8 + ((w8 - 1) * 4)), (float) (((h8 - 1) * 4) + h8), p8);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) ((((canvas.getWidth() - (width8 * 4)) - 4) - w8) + w8 + ((w8 - 1) * 4)), (float) (((h8 - 1) * 4) + h8), p26);
                        }
                    }
                }
                b8.recycle();
                return;
            case 9:
                String text9 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                Typeface f9 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f9 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint9 = new Paint();
                paint9.setColor(-65536);
                paint9.setTextSize((float) fontSize);
                paint9.setTypeface(f9);
                Paint.FontMetrics fm9 = paint9.getFontMetrics();
                if (0 == 1) {
                    paint9.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint9.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint9.setFakeBoldText(true);
                    paint9.setTextSkewX(-0.5f);
                }
                Bitmap b9 = Bitmap.createBitmap(getFontWidth(paint9, text9), Math.round(fm9.bottom - fm9.ascent), Bitmap.Config.ARGB_8888);
                Canvas c9 = new Canvas(b9);
                c9.drawARGB(0, 0, 0, 0);
                c9.drawText(text9, 0.0f, getFontTop(paint9) - 1.0f, paint9);
                Paint p9 = new Paint();
                p9.setColor(-65536);
                Paint p27 = new Paint();
                p27.setColor(-16777216);
                int width9 = b9.getWidth();
                int height9 = MainActivity.pix > b9.getHeight() ? b9.getHeight() : MainActivity.pix;
                RectF r3 = new RectF();
                r3.left = (float) ((canvas.getWidth() / 2) - (width9 * 2));
                r3.top = 0.0f;
                r3.bottom = (float) (MainActivity.pix * 5);
                r3.right = (float) (((canvas.getWidth() / 2) + (width9 * 3)) - 1);
                paint9.setColor(-16777216);
                canvas.drawRect(r3, paint9);
                for (int h9 = 1; h9 <= height9; h9++) {
                    for (int w9 = 1; w9 <= width9; w9++) {
                        if (b9.getPixel(w9 - 1, h9 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) (((canvas.getWidth() / 2) - (width9 * 2)) + w9 + ((w9 - 1) * 4)), (float) (((h9 - 1) * 4) + h9), p9);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) (((canvas.getWidth() / 2) - (width9 * 2)) + w9 + ((w9 - 1) * 4)), (float) (((h9 - 1) * 4) + h9), p27);
                        }
                    }
                }
                b9.recycle();
                return;
            case MotionEventCompat.ACTION_HOVER_EXIT:
                String text10 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Typeface f10 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f10 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint10 = new Paint();
                paint10.setColor(-65536);
                paint10.setTextSize((float) fontSize);
                paint10.setTypeface(f10);
                Paint.FontMetrics fm10 = paint10.getFontMetrics();
                if (0 == 1) {
                    paint10.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint10.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint10.setFakeBoldText(true);
                    paint10.setTextSkewX(-0.5f);
                }
                Bitmap b10 = Bitmap.createBitmap(getFontWidth(paint10, text10), Math.round(fm10.bottom - fm10.ascent), Bitmap.Config.ARGB_8888);
                Canvas c10 = new Canvas(b10);
                c10.drawARGB(0, 0, 0, 0);
                c10.drawText(text10, 0.0f, getFontTop(paint10) - 1.0f, paint10);
                Paint p10 = new Paint();
                p10.setColor(-65536);
                new Paint().setColor(-16777216);
                int width10 = b10.getWidth();
                left = (width10 * 5) - 1;
                int height10 = MainActivity.pix > b10.getHeight() ? b10.getHeight() : MainActivity.pix;
                for (int h10 = 1; h10 <= height10; h10++) {
                    for (int w10 = 1; w10 <= width10; w10++) {
                        if (b10.getPixel(w10 - 1, h10 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) (((w10 - 1) * 4) + w10), (float) (((h10 - 1) * 4) + h10), p10);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) (((w10 - 1) * 4) + w10), (float) (((h10 - 1) * 4) + h10), p10);
                        }
                    }
                }
                b10.recycle();
                return;
            case 11:
                String text11 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Typeface f11 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f11 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint11 = new Paint();
                paint11.setColor(-65536);
                paint11.setTextSize((float) fontSize);
                paint11.setTypeface(f11);
                Paint.FontMetrics fm11 = paint11.getFontMetrics();
                if (0 == 1) {
                    paint11.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint11.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint11.setFakeBoldText(true);
                    paint11.setTextSkewX(-0.5f);
                }
                Bitmap b11 = Bitmap.createBitmap(getFontWidth(paint11, text11), Math.round(fm11.bottom - fm11.ascent), Bitmap.Config.ARGB_8888);
                Canvas c11 = new Canvas(b11);
                c11.drawARGB(0, 0, 0, 0);
                c11.drawText(text11, 0.0f, getFontTop(paint11) - 1.0f, paint11);
                Paint p11 = new Paint();
                p11.setColor(-65536);
                Paint p28 = new Paint();
                p28.setColor(-16777216);
                int width11 = b11.getWidth();
                int height11 = MainActivity.pix > b11.getHeight() ? b11.getHeight() : MainActivity.pix;
                for (int h11 = 1; h11 <= height11; h11++) {
                    for (int w11 = 1; w11 <= width11; w11++) {
                        if (b11.getPixel(w11 - 1, h11 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) ((((canvas.getWidth() - (width11 * 4)) - 4) - w11) + w11 + ((w11 - 1) * 4)), (float) (((h11 - 1) * 4) + h11), p11);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) ((((canvas.getWidth() - (width11 * 4)) - 4) - w11) + w11 + ((w11 - 1) * 4)), (float) (((h11 - 1) * 4) + h11), p28);
                        }
                    }
                }
                b11.recycle();
                return;
            case 12:
                String text12 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Typeface f12 = Typeface.DEFAULT;
                if (Integer.parseInt(OpenFileDialog.sEmpty) != 0 && OpenFileDialog.sEmpty.length() > 0) {
                    f12 = Typeface.createFromFile(OpenFileDialog.sEmpty);
                }
                Paint paint12 = new Paint();
                paint12.setColor(-65536);
                paint12.setTextSize((float) fontSize);
                paint12.setTypeface(f12);
                Paint.FontMetrics fm12 = paint12.getFontMetrics();
                if (0 == 1) {
                    paint12.setFakeBoldText(true);
                } else if (0 == 2) {
                    paint12.setTextSkewX(-0.5f);
                } else if (0 == 3) {
                    paint12.setFakeBoldText(true);
                    paint12.setTextSkewX(-0.5f);
                }
                Bitmap b12 = Bitmap.createBitmap(getFontWidth(paint12, text12), Math.round(fm12.bottom - fm12.ascent), Bitmap.Config.ARGB_8888);
                Canvas c12 = new Canvas(b12);
                c12.drawARGB(0, 0, 0, 0);
                c12.drawText(text12, 0.0f, getFontTop(paint12) - 1.0f, paint12);
                Paint p12 = new Paint();
                p12.setColor(-65536);
                Paint p29 = new Paint();
                p29.setColor(-16777216);
                int width12 = b12.getWidth();
                int height12 = MainActivity.pix > b12.getHeight() ? b12.getHeight() : MainActivity.pix;
                RectF r4 = new RectF();
                r4.left = (float) ((canvas.getWidth() / 2) - (width12 * 2));
                r4.top = 0.0f;
                r4.bottom = (float) (MainActivity.pix * 5);
                r4.right = (float) (((canvas.getWidth() / 2) + (width12 * 3)) - 1);
                paint12.setColor(-16777216);
                canvas.drawRect(r4, paint12);
                for (int h12 = 1; h12 <= height12; h12++) {
                    for (int w12 = 1; w12 <= width12; w12++) {
                        if (b12.getPixel(w12 - 1, h12 - 1) != 0) {
                            canvas.drawBitmap(MainActivity.ledL, (float) (((canvas.getWidth() / 2) - (width12 * 2)) + w12 + ((w12 - 1) * 4)), (float) (((h12 - 1) * 4) + h12), p12);
                        } else {
                            canvas.drawBitmap(MainActivity.ledH, (float) (((canvas.getWidth() / 2) - (width12 * 2)) + w12 + ((w12 - 1) * 4)), (float) (((h12 - 1) * 4) + h12), p29);
                        }
                    }
                }
                b12.recycle();
                return;
            case 13:
                String font = MainActivity.logFont;
                int fontSize2 = MainActivity.logFontSize;
                int fontStyle = MainActivity.logFontStyle;
                if (MainActivity.logContext.length() != 0) {
                    new Paint().setColor(-16777216);
                    if (MainActivity.logFlag.equals("0")) {
                        String text13 = MainActivity.logContext;
                        if (text13.length() != 0) {
                            Typeface f13 = Typeface.DEFAULT;
                            if (font != null && font.length() > 0) {
                                f13 = Typeface.createFromFile(font);
                            }
                            Paint paint13 = new Paint();
                            paint13.setColor(-65536);
                            paint13.setTextSize((float) fontSize2);
                            paint13.setTypeface(f13);
                            Paint.FontMetrics fm13 = paint13.getFontMetrics();
                            if (fontStyle == 1) {
                                paint13.setFakeBoldText(true);
                            } else if (fontStyle == 2) {
                                paint13.setTextSkewX(-0.5f);
                            } else if (fontStyle == 3) {
                                paint13.setFakeBoldText(true);
                                paint13.setTextSkewX(-0.5f);
                            }
                            Bitmap b13 = Bitmap.createBitmap(getFontWidth(paint13, text13), Math.round(fm13.bottom - fm13.ascent), Bitmap.Config.ARGB_8888);
                            Canvas c13 = new Canvas(b13);
                            c13.drawARGB(0, 0, 0, 0);
                            c13.drawText(text13, 0.0f, getFontTop(paint13) - 1.0f, paint13);
                            Paint p13 = new Paint();
                            p13.setColor(-65536);
                            int width13 = b13.getWidth() > MainActivity.pix * 4 ? MainActivity.pix * 4 : b13.getWidth();
                            left = (width13 * 5) - 1;
                            int height13 = MainActivity.pix > b13.getHeight() ? b13.getHeight() : MainActivity.pix;
                            for (int h13 = 1; h13 <= height13; h13++) {
                                for (int w13 = 1; w13 <= width13; w13++) {
                                    if (b13.getPixel(w13 - 1, h13 - 1) != 0) {
                                        canvas.drawBitmap(MainActivity.ledL, (float) (((w13 - 1) * 4) + w13), (float) (((h13 - 1) * 4) + h13), p13);
                                    } else {
                                        canvas.drawBitmap(MainActivity.ledH, (float) (((w13 - 1) * 4) + w13), (float) (((h13 - 1) * 4) + h13), p13);
                                    }
                                }
                            }
                            b13.recycle();
                            return;
                        }
                        return;
                    }
                    try {
                        Bitmap bitmap = BitmapFactory.decodeFile(MainActivity.logContext);
                        int width14 = bitmap.getWidth() > MainActivity.pix * 4 ? MainActivity.pix * 4 : bitmap.getWidth();
                        int height14 = MainActivity.pix > bitmap.getHeight() ? bitmap.getHeight() : MainActivity.pix;
                        Paint p14 = new Paint();
                        p14.setColor(-65536);
                        left = (width14 * 5) - 1;
                        for (int h14 = 1; h14 <= height14; h14++) {
                            for (int w14 = 1; w14 <= width14; w14++) {
                                if (getColorLD(bitmap.getPixel(w14 - 1, h14 - 1)) > 50.0f) {
                                    canvas.drawBitmap(MainActivity.ledL, (float) (((w14 - 1) * 4) + w14), (float) (((h14 - 1) * 4) + h14), p14);
                                } else {
                                    canvas.drawBitmap(MainActivity.ledH, (float) (((w14 - 1) * 4) + w14), (float) (((h14 - 1) * 4) + h14), p14);
                                }
                            }
                        }
                        bitmap.recycle();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    return;
                }
            case 14:
                String font2 = MainActivity.logFont;
                int fontSize3 = MainActivity.logFontSize;
                int fontStyle2 = MainActivity.logFontStyle;
                if (MainActivity.logContext.length() != 0) {
                    Paint p210 = new Paint();
                    p210.setColor(-16777216);
                    if (MainActivity.logFlag.equals("0")) {
                        String text14 = MainActivity.logContext;
                        if (text14.length() != 0) {
                            Typeface f14 = Typeface.DEFAULT;
                            if (font2 != null && font2.length() > 0) {
                                f14 = Typeface.createFromFile(font2);
                            }
                            Paint paint14 = new Paint();
                            paint14.setColor(-65536);
                            paint14.setTextSize((float) fontSize3);
                            paint14.setTypeface(f14);
                            Paint.FontMetrics fm14 = paint14.getFontMetrics();
                            if (fontStyle2 == 1) {
                                paint14.setFakeBoldText(true);
                            } else if (fontStyle2 == 2) {
                                paint14.setTextSkewX(-0.5f);
                            } else if (fontStyle2 == 3) {
                                paint14.setFakeBoldText(true);
                                paint14.setTextSkewX(-0.5f);
                            }
                            Bitmap b14 = Bitmap.createBitmap(getFontWidth(paint14, text14), Math.round(fm14.bottom - fm14.ascent), Bitmap.Config.ARGB_8888);
                            Canvas c14 = new Canvas(b14);
                            c14.drawARGB(0, 0, 0, 0);
                            c14.drawText(text14, 0.0f, getFontTop(paint14) - 1.0f, paint14);
                            Paint p15 = new Paint();
                            p15.setColor(-65536);
                            int width15 = b14.getWidth() > MainActivity.pix * 4 ? MainActivity.pix * 4 : b14.getWidth();
                            int height15 = MainActivity.pix > b14.getHeight() ? b14.getHeight() : MainActivity.pix;
                            for (int h15 = 1; h15 <= height15; h15++) {
                                for (int w15 = 1; w15 <= width15; w15++) {
                                    if (b14.getPixel(w15 - 1, h15 - 1) != 0) {
                                        canvas.drawBitmap(MainActivity.ledL, (float) ((((canvas.getWidth() - (width15 * 4)) - 4) - w15) + w15 + ((w15 - 1) * 4)), (float) (((h15 - 1) * 4) + h15), p15);
                                    } else {
                                        canvas.drawBitmap(MainActivity.ledH, (float) ((((canvas.getWidth() - (width15 * 4)) - 4) - w15) + w15 + ((w15 - 1) * 4)), (float) (((h15 - 1) * 4) + h15), p210);
                                    }
                                }
                            }
                            b14.recycle();
                            return;
                        }
                        return;
                    }
                    try {
                        Bitmap bitmap2 = BitmapFactory.decodeFile(MainActivity.logContext);
                        int width16 = bitmap2.getWidth() > MainActivity.pix * 4 ? MainActivity.pix * 4 : bitmap2.getWidth();
                        int height16 = MainActivity.pix > bitmap2.getHeight() ? bitmap2.getHeight() : MainActivity.pix;
                        Paint p16 = new Paint();
                        p16.setColor(-65536);
                        for (int h16 = 1; h16 <= height16; h16++) {
                            for (int w16 = 1; w16 <= width16; w16++) {
                                if (getColorLD(bitmap2.getPixel(w16 - 1, h16 - 1)) > 50.0f) {
                                    canvas.drawBitmap(MainActivity.ledL, (float) ((((canvas.getWidth() - (width16 * 4)) - 4) - w16) + w16 + ((w16 - 1) * 4)), (float) (((h16 - 1) * 4) + h16), p16);
                                } else {
                                    canvas.drawBitmap(MainActivity.ledH, (float) ((((canvas.getWidth() - (width16 * 4)) - 4) - w16) + w16 + ((w16 - 1) * 4)), (float) (((h16 - 1) * 4) + h16), p210);
                                }
                            }
                        }
                        bitmap2.recycle();
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                } else {
                    return;
                }
            case ViewDragHelper.EDGE_ALL:
                String font3 = MainActivity.logFont;
                int fontSize4 = MainActivity.logFontSize;
                int fontStyle3 = MainActivity.logFontStyle;
                if (MainActivity.logContext.length() != 0) {
                    Paint p211 = new Paint();
                    p211.setColor(-16777216);
                    if (MainActivity.logFlag.equals("0")) {
                        String text15 = MainActivity.logContext;
                        if (text15.length() != 0) {
                            Typeface f15 = Typeface.DEFAULT;
                            if (font3 != null && font3.length() > 0) {
                                f15 = Typeface.createFromFile(font3);
                            }
                            Paint paint15 = new Paint();
                            paint15.setColor(-65536);
                            paint15.setTextSize((float) fontSize4);
                            paint15.setTypeface(f15);
                            Paint.FontMetrics fm15 = paint15.getFontMetrics();
                            if (fontStyle3 == 1) {
                                paint15.setFakeBoldText(true);
                            } else if (fontStyle3 == 2) {
                                paint15.setTextSkewX(-0.5f);
                            } else if (fontStyle3 == 3) {
                                paint15.setFakeBoldText(true);
                                paint15.setTextSkewX(-0.5f);
                            }
                            Bitmap b15 = Bitmap.createBitmap(getFontWidth(paint15, text15), Math.round(fm15.bottom - fm15.ascent), Bitmap.Config.ARGB_8888);
                            Canvas c15 = new Canvas(b15);
                            c15.drawARGB(0, 0, 0, 0);
                            c15.drawText(text15, 0.0f, getFontTop(paint15) - 1.0f, paint15);
                            Paint p17 = new Paint();
                            p17.setColor(-65536);
                            int width17 = b15.getWidth() > MainActivity.pix * 4 ? MainActivity.pix * 4 : b15.getWidth();
                            int height17 = MainActivity.pix > b15.getHeight() ? b15.getHeight() : MainActivity.pix;
                            RectF r5 = new RectF();
                            r5.left = (float) ((canvas.getWidth() / 2) - (width17 * 2));
                            r5.top = 0.0f;
                            r5.bottom = (float) (MainActivity.pix * 5);
                            r5.right = (float) (((canvas.getWidth() / 2) + (width17 * 3)) - 1);
                            paint15.setColor(-16777216);
                            canvas.drawRect(r5, paint15);
                            for (int h17 = 1; h17 <= height17; h17++) {
                                for (int w17 = 1; w17 <= width17; w17++) {
                                    if (b15.getPixel(w17 - 1, h17 - 1) != 0) {
                                        canvas.drawBitmap(MainActivity.ledL, (float) (((canvas.getWidth() / 2) - (width17 * 2)) + w17 + ((w17 - 1) * 4)), (float) (((h17 - 1) * 4) + h17), p17);
                                    } else {
                                        canvas.drawBitmap(MainActivity.ledH, (float) (((canvas.getWidth() / 2) - (width17 * 2)) + w17 + ((w17 - 1) * 4)), (float) (((h17 - 1) * 4) + h17), p211);
                                    }
                                }
                            }
                            b15.recycle();
                            return;
                        }
                        return;
                    }
                    try {
                        Bitmap bitmap3 = BitmapFactory.decodeFile(MainActivity.logContext);
                        int width18 = bitmap3.getWidth() > MainActivity.pix * 4 ? MainActivity.pix * 4 : bitmap3.getWidth();
                        int height18 = MainActivity.pix > bitmap3.getHeight() ? bitmap3.getHeight() : MainActivity.pix;
                        Paint p18 = new Paint();
                        p18.setColor(-65536);
                        RectF r6 = new RectF();
                        r6.left = (float) ((canvas.getWidth() / 2) - (width18 * 2));
                        r6.top = 0.0f;
                        r6.bottom = (float) (MainActivity.pix * 5);
                        r6.right = (float) (((canvas.getWidth() / 2) + (width18 * 3)) - 1);
                        p18.setColor(-16777216);
                        canvas.drawRect(r6, p18);
                        for (int h18 = 1; h18 <= height18; h18++) {
                            for (int w18 = 1; w18 <= width18; w18++) {
                                if (getColorLD(bitmap3.getPixel(w18 - 1, h18 - 1)) > 50.0f) {
                                    canvas.drawBitmap(MainActivity.ledL, (float) (((canvas.getWidth() / 2) - (width18 * 2)) + w18 + ((w18 - 1) * 4)), (float) (((h18 - 1) * 4) + h18), p18);
                                } else {
                                    canvas.drawBitmap(MainActivity.ledH, (float) (((canvas.getWidth() / 2) - (width18 * 2)) + w18 + ((w18 - 1) * 4)), (float) (((h18 - 1) * 4) + h18), p211);
                                }
                            }
                        }
                        bitmap3.recycle();
                        return;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        return;
                    }
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public static float getColorLD(int rgb) {
        return (float) ((((double) Color.red(rgb)) * 0.3d) + (((double) Color.green(rgb)) * 0.59d) + (((double) Color.blue(rgb)) * 0.11d));
    }

    public static float getFontTop(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    public static int getFontWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil((double) widths[j]);
            }
        }
        return iRet;
    }

    class DrawThread extends Thread {
        DrawThread() {
        }

        public void run() {
            new Timer().schedule(new TimerTask() {
                public void run() {
                    if (!ProtocolData.sendBitmapLog) {
                        for (int i = 0; i < 8; i++) {
                            MainActivity.f3pf.save(i);
                            View v = MainActivity.f1lv.getChildAt(i);
                            if (v != null) {
                                Map<String, String> m = MainActivity.data.get(v.getId());
                                if (m.get("flag").equals("0")) {
                                    m.put("context", ((EditText) v.findViewById(R.id.context)).getText().toString());
                                }
                            }
                        }
                        Canvas canvas = ShowView.this.holder.lockCanvas();
                        if (canvas != null) {
                            canvas.drawColor(-16777216);
                            ShowView.this.drawBg(canvas);
                            if (MainActivity.selectRow > -1) {
                                Map<String, String> map = MainActivity.data.get(MainActivity.selectRow);
                                if (map.get("flag").equals("0")) {
                                    ShowView.this.drawText(canvas, map.get("context"), map.get("fontFile"), Integer.parseInt(map.get("fontSize")), Integer.parseInt(map.get("fontStyle")));
                                } else {
                                    ShowView.this.drawImage(canvas, map.get("context"));
                                }
                                ShowView.left = 0;
//                                ShowView.drawQianru(canvas, map);
                                if (ShowView.left % 8 != 0) {
                                    ShowView.left += 8 - (ShowView.left % 8);
                                    if (ShowView.left % 2 != 0) {
                                        ShowView.left++;
                                    }
                                }
                            }
                            if (canvas != null) {
                                ShowView.this.holder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                }
            }, 1000, 1000);
        }
    }
}
