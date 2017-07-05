package com.bairong.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.bairong.sample.bean.Student;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by zhangwei on 17/3/20.
 */

public class RxjavaTest extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private Observable observable;
    private Subscriber subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_test);
        button = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.tv);

        RxView.clicks(button)
                .throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //simple();
                        //simpleFrom();
                        //map();
                        //flatmap();
                        //lift();
                        compose();
                    }
                });

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (observable!=null&&subscriber!=null){
                    observable.subscribe( subscriber);
                }
            }
        });
        observable=Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello world");
            }
        });
        subscriber=new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                textView.setText(o.toString());
            }
        };
        RxView.clicks(button).throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(RxjavaTest.this,"throttleFirst test",Toast.LENGTH_SHORT).show();
                    }
                });*/

    }

    private void simple() {
        Observable.just("one", "two", "three")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("call", s);
                    }
                });
    }

    private void simpleFrom() {
        String[] words = {"fOne", "fTwo", "fThree"};
        Observable.from(words)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("fCall", s);
                    }
                });

    }

    private void map() {
        Observable.just("mOne", "mTwo")
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return "map:" + s;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.e("maps", o.toString());
                    }
                });
    }

    private void flatmap() {
        Student student = new Student();
        List courses = new ArrayList();
        courses.add("Chinese");
        courses.add("English");
        courses.add("math");
        student.setCourses(courses);

        Student student2 = new Student();
        List courses2 = new ArrayList();
        courses2.add("Chinese2");
        courses2.add("English2");
        courses2.add("math2");
        student2.setCourses(courses2);
        Observable.just(student, student2)
                .flatMap(new Func1<Student, Observable<?>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.e("flatmap", o.toString());
                    }
                });
    }

    private void lift() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("one");
            }
        });
        Observable observable1 = observable.lift(new Observable.Operator<String, String>() {
            @Override
            public Subscriber<? super String> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        subscriber.onNext("new" + s);
                    }
                };
            }
        });
        observable1.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.e("aaa", o.toString());
            }
        });

        //observable1.subscribe(new )
    }

    private void compose() {
        ListAllTranformer listAllTranformer = new ListAllTranformer();
        Observable.just("one","two")
                .compose(listAllTranformer)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("compose",s);
                    }
                });
        Observable.just("yi","er")
                .compose(listAllTranformer)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("compose2",s);
                    }
                });
    }

    class ListAllTranformer implements Observable.Transformer<String, String> {

        @Override
        public Observable<String> call(Observable<String> stringObservable) {
            return stringObservable
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            return s + "1";
                        }
                    })
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            return s+"2";
                        }
                    })
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            return s+"3";
                        }
                    });
        }
    }

    private void simulateRxview(){
        Observable.just("one")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                });
    }
}
