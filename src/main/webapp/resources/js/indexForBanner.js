$('#nav li:nth-of-type(2)').addClass('clicked');

/* 각 mbti 캐릭터 랜덤 등장 */
let num = 0;
for (let i = 1; i <= 16; i++) {
    $('.avatar' + i + '').css({ opacity: 0 })
    num++;
    let random = Math.floor(Math.random() * 2000) + 500;
    for (let i = 0; i < 1; i++) {

        $('.avatar' + num + '').delay(random).animate({ opacity: 1 }, 500)
    }
}



let INTJTip = '<p class="INTJTip MBTITip"><span class="analysisTypeSpan">“용의주도한 전략가”</span><br><br>윗자리에 있는 사람은 고독한 법, 전략적 사고에 뛰어나며 매우 극소수인 건축가형 사람은 이를 누구보다 뼈저리게 이해합니다. </p>';
let INTPTip = '<p class="INTPTip MBTITip"><span class="analysisTypeSpan">“논리적인 사색가”</span><br><br>이 유형의 사람은 그들이 가진 독창성과 창의력, 그리고 그들만의 왕성한 지적 호기심에 나름의 자부심을 가지고 있습니다. 보통 철학자나 사색가, 혹은 몽상에 빠진 천재 교수로도 많이 알려져있습니다.</p>';
let ENTJTip = '<p class="ENTJTip MBTITip"><span class="analysisTypeSpan">“대담한 통솔자”</span><br><br>통솔자형 사람은 천성적으로 타고난 리더입니다. 이 유형에 속하는 사람은 넘치는 카리스마와 자신감으로 공통의 목표 실현을 위해 다른 이들을 이끌고 진두지휘합니다.</p>';
let ENTPTip = '<p class="ENTPTip MBTITip"><span class="analysisTypeSpan">“뜨거운 논쟁을 즐기는 변론가”</span><br><br>변론가형 사람은 타인이 믿는 이념이나 논쟁에 반향을 일으킴으로써 군중을 선동하는 일명 선의의 비판자입니다.</p>';

let INFJTip = '<p class="INFJTip MBTITip"><span class="diplomacyTypeSpan">“선의의 옹호자”</span><br><br>선의의 옹호자형은 가장 흔치 않은 성격 유형으로 인구의 채 1%도 되지 않습니다. 그럼에도 불구하고 나름의 고유 성향으로 세상에서 그들만의 입지를 확고히 다집니다.</p>';
let INFPTip = '<p class="INFPTip MBTITip"><span class="diplomacyTypeSpan">“열정적인 중재자”</span><br><br>중재자형 사람은 최악의 상황이나 악한 사람에게서도 좋은 면만을 바라보며 긍정적이고 더 나은 상황을 만들고자 노력하는 진정한 이상주의자입니다.</p>';
let ENFJTip = '<p class="ENFJTip MBTITip"><span class="diplomacyTypeSpan">“정의로운 사회운동가”</span><br><br>사회운동가형 사람은 카리스마와 충만한 열정을 지닌 타고난 리더형입니다. 이들은 다른 이들로 하여금 그들의 꿈을 이루며, 선한 일을 통하여 세상에 빛과 소금이 될 수 있도록 사람들을 독려합니다.</p>';
let ENFPTip = '<p class="ENFPTip MBTITip"><span class="diplomacyTypeSpan">“재기발랄한 활동가”</span><br><br>종종 분위기 메이커 역할을 하기도 하는 이들은 단순한 인생의 즐거움이나 그때그때 상황에서 주는 일시적인 만족이 아닌 타인과 사회적, 정서적으로 깊은 유대 관계를 맺음으로써 행복을 느낍니다.</p>';

let ISTJTip = '<p class="ISTJTip MBTITip"><span class="administratorTypeSpan">“청렴결백한 논리주의자”</span><br><br>논리주의자형은 가장 다수의 사람이 속하는 성격 유형으로 인구의 대략 13%를 차지합니다. 이 유형의 사람은 자신이 맡은 바 책임을 다하며 그들이 하는 일에 큰 자부심을 가지고 있습니다.</p>';
let ISFJTip = '<p class="ISFJTip MBTITip"><span class="administratorTypeSpan">“용감한 수호자”</span><br><br>타인을 향한 연민이나 동정심이 있으면서도 가족이나 친구를 보호해야 할 때는 가차 없는 모습을 보이기도 합니다. 조용하고 내성적인 반면 관계술에 뛰어나 인간관계를 잘 만들어갑니다. </p>';
let ESTJTip = '<p class="ESTJTip MBTITip"><span class="administratorTypeSpan">“엄격한 관리자”</span><br><br>정직하고 헌신적이며 위풍당당한 이들은 비록 험난한 가시밭길이라도 조언을 통하여 그들이 옳다고 생각하는 길로 사람들을 인도합니다. </p>';
let ESFJTip = '<p class="ESFJTip MBTITip"><span class="administratorTypeSpan">“사교적인 외교관”</span><br><br>사교형 사람을 한마디로 정의 내리자면 이들은 인기쟁이입니다. 이들은 또한 훗날 다양한 사교 모임이나 어울림을 통해 주위 사람들에게 끊임없는 관심과 애정을 보임으로써 다른 이들을 행복하고 즐겁게 해주고자 노력합니다.</p>';

let ISTPTip = '<p class="ISTPTip MBTITip"><span class="explorerTypeSpan">“만능 재주꾼”</span><br><br>냉철한 이성주의적 성향과 왕성한 호기심을 가진 만능재주꾼형 사람은 직접 손으로 만지고 눈으로 보면서 주변 세상을 탐색하는 것을 좋아합니다.</p>';
let ISFPTip = '<p class="ISFPTip MBTITip"><span class="explorerTypeSpan">“호기심 많은 예술가”</span><br><br>이들은 다양한 아이디어나 사람들로부터 영감을 받아 다채로운 삶을 살아갑니다. 그들이 받은 영감을 본인만의 시각으로 재해석하여 새로운 것을 발견하며 즐거움을 느끼기는 그 어떤 유형보다 탐험이나 실험 정신이 뛰어납니다.</p>';
let ESTPTip = '<p class="ESTPTip MBTITip"><span class="explorerTypeSpan">“모험을 즐기는 사업가”</span><br><br>친근한 농담으로 주변 사람을 웃게 만드는 이들은 주변의 이목을 끄는 것을 좋아합니다. 만일 관객 중 무대에 올라올 사람을 호명하는 경우, 이들은 제일 먼저 손을 들거나 친구를 대신하여 망설임 없이 무대에 올라서기도 합니다.</p>';
let ESFPTip = '<p class="ESFPTip MBTITip"><span class="explorerTypeSpan">“자유로운 영혼의 연예인”</span><br><br>갑자기 흥얼거리며 즉흥적으로 춤을 추기 시작하는 누군가가 있다면 이는 연예인형의 사람일 가능성이 큽니다. 이들은 순간의 흥분되는 감정이나 상황에 쉽게 빠져들며, 주위 사람들 역시 그런 느낌을 만끽하기를 원합니다.</p>';

//$('#mbtiImg').append('<img src="../myapp/webapp/resources/img/avatar/MBTI_INTJ.png" alt="INTJ" id="cardAvatar" class="avatar avatar1 INTJ"></img>');

$('#mbtiImg').append('<img src="../myapp/resources/img/avatar/MBTI_INTJ.png" alt="INTJ" id="cardAvatar" class="avatar avatar1 INTJ"></img>');
$('#mbtiImg').append(INTJTip)

$('.cardTitle ul li').on('click', function () {
    let mbtiTxt = $(this).text();
    let cardImg = '<img src="../myapp/resources/img/avatar/MBTI_' + mbtiTxt + '.png" alt="' + mbtiTxt + '" id="cardAvatar" class="avatar avatar1 ' + mbtiTxt + '"></img>';

    $('#mbtiImg').empty();
    $('#mbtiImg').append(cardImg);

    if (mbtiTxt == 'INTJ') {
        $('#mbtiImg').append(INTJTip);
    } else if (mbtiTxt == 'INTP') {
        $('#mbtiImg').append(INTPTip);
    } else if (mbtiTxt == 'ENTJ') {
        $('#mbtiImg').append(ENTJTip);
    } else if (mbtiTxt == 'ENTP') {
        $('#mbtiImg').append(ENTPTip);
    } else if (mbtiTxt == 'INFJ') {
        $('#mbtiImg').append(INFJTip);
    } else if (mbtiTxt == 'INFP') {
        $('#mbtiImg').append(INFPTip);
    } else if (mbtiTxt == 'ENFJ') {
        $('#mbtiImg').append(ENFJTip);
    } else if (mbtiTxt == 'ENFP') {
        $('#mbtiImg').append(ENFPTip);
    } else if (mbtiTxt == 'ISTJ') {
        $('#mbtiImg').append(ISTJTip);
    } else if (mbtiTxt == 'ISFJ') {
        $('#mbtiImg').append(ISFJTip);
    } else if (mbtiTxt == 'ESTJ') {
        $('#mbtiImg').append(ESTJTip);
    } else if (mbtiTxt == 'ESFJ') {
        $('#mbtiImg').append(ESFJTip);
    } else if (mbtiTxt == 'ISTP') {
        $('#mbtiImg').append(ISTPTip);
    } else if (mbtiTxt == 'ISFP') {
        $('#mbtiImg').append(ISFPTip);
    } else if (mbtiTxt == 'ESTP') {
        $('#mbtiImg').append(ESTPTip);
    } else if (mbtiTxt == 'ESFP') {
        $('#mbtiImg').append(ESFPTip);
    }

})
